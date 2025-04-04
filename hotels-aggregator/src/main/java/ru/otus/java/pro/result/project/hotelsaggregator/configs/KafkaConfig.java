package ru.otus.java.pro.result.project.hotelsaggregator.configs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.errors.RecordDeserializationException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.*;
import org.springframework.kafka.requestreply.AggregatingReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;
import ru.otus.java.pro.result.project.hotelsaggregator.configs.properties.KafkaPropertyConfig;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.Provider;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.ProviderApi;
import ru.otus.java.pro.result.project.hotelsaggregator.enums.BusinessMethodEnum;

import java.net.SocketTimeoutException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Getter
@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableKafka
public class KafkaConfig {

    private final KafkaPropertyConfig kafkaPropertyConfig;

    private final List<Provider> providers;

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(getConsumerConfig());
    }

    @Bean
    public ConsumerFactory<String, Collection<ConsumerRecord<String, Object>>> consumerAggregateFactory() {
        return new DefaultKafkaConsumerFactory<>(getConsumerConfig());
    }

    private Map<String, Object> getConsumerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaPropertyConfig.getBrokers());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaPropertyConfig.getConsumerGroup());
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(JsonDeserializer.TYPE_MAPPINGS, "providerResponseDto:ru.otus.java.pro.result.project.hotelsaggregator.dtos.messages.ProviderResponseDto, " +
                "hotelDto:ru.otus.java.pro.result.project.hotelsaggregator.dtos.HotelDto");
        if (kafkaPropertyConfig.isAsyncModeEnabled()) {
            config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        }
        return config;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(getProducerConfig());
    }

    private Map<String, Object> getProducerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaPropertyConfig.getBrokers());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(JsonSerializer.TYPE_MAPPINGS, "hotelDtoRq:ru.otus.java.pro.result.project.hotelsaggregator.dtos.HotelDtoRq");
        return config;
    }

    @ConditionalOnExpression(value = "${spring.kafka.async-mode.enable:false}")
    @Bean
    public AggregatingReplyingKafkaTemplate<String, Object, Object> kafkaAggregatingTemplate(
            ProducerFactory<String, Object> producerFactory,
            ConcurrentMessageListenerContainer<String, Collection<ConsumerRecord<String, Object>>> repliesAggregateContainer,
            List<Provider> providers) {

        AtomicInteger releaseCount = new AtomicInteger();
        AggregatingReplyingKafkaTemplate<String, Object, Object> template = new AggregatingReplyingKafkaTemplate<>(
                producerFactory, repliesAggregateContainer, (list, timeout) -> {
            releaseCount.incrementAndGet();
            return list.size() == providers.size();
        });
        template.setReturnPartialOnTimeout(kafkaPropertyConfig.getAsyncMode().isPartialOnTimeout());
        template.setDefaultReplyTimeout(kafkaPropertyConfig.getAsyncMode().getCommonReplyTimeout());
        log.info("Kafka running in aggregate replies mode (async): one request per topic");
        return template;
    }

    @ConditionalOnBean(name = "kafkaAggregatingTemplate")
    @Bean
    public ConcurrentMessageListenerContainer<String, Collection<ConsumerRecord<String, Object>>> repliesAggregateContainer(
            ConcurrentKafkaListenerContainerFactory<String, Collection<ConsumerRecord<String, Object>>> containerFactory,
            ConsumerFactory<String, Collection<ConsumerRecord<String, Object>>> consumerAggregateFactory,
            DefaultErrorHandler errorHandler) {

        return createReplyContainer(containerFactory, consumerAggregateFactory, errorHandler);
    }

    @ConditionalOnMissingBean(AggregatingReplyingKafkaTemplate.class)
    @Bean
    public ReplyingKafkaTemplate<String, Object, Object> kafkaReplyingTemplate(
            ProducerFactory<String, Object> producerFactory,
            ConsumerFactory<String, Object> consumerFactory,
            ConcurrentMessageListenerContainer<String, Object> repliesContainer) {

        ReplyingKafkaTemplate<String, Object, Object> replyingTemplate = new ReplyingKafkaTemplate<>(producerFactory, repliesContainer);
        replyingTemplate.setConsumerFactory(consumerFactory);
        replyingTemplate.setDefaultReplyTimeout(kafkaPropertyConfig.getCommonReplyTimeout());
        log.info("Kafka running in sync request/reply mode: one request per multi topics");
        return replyingTemplate;
    }

    @ConditionalOnBean(name = "kafkaReplyingTemplate")
    @Bean
    public ConcurrentMessageListenerContainer<String, Object> repliesContainer(
            ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory,
            ConsumerFactory<String, Object> consumerFactory,
            DefaultErrorHandler errorHandler) {
        return createReplyContainer(containerFactory, consumerFactory, errorHandler);
    }

    private <T> ConcurrentMessageListenerContainer<String, T> createReplyContainer(
            ConcurrentKafkaListenerContainerFactory<String, T> containerFactory,
            ConsumerFactory<String, T> consumerFactory, DefaultErrorHandler errorHandler) {

        containerFactory.setConsumerFactory(consumerFactory);
        containerFactory.setCommonErrorHandler(errorHandler);
        if (kafkaPropertyConfig.isAsyncModeEnabled()) {
            containerFactory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        }
        List<String> listReplyTopics = getAllTopicNames(true);
        String[] arrReplyTopics = new String[listReplyTopics.size()];
        arrReplyTopics = listReplyTopics.toArray(arrReplyTopics);
        ConcurrentMessageListenerContainer<String, T> repliesContainer = containerFactory.createContainer(arrReplyTopics);
        repliesContainer.getContainerProperties().setGroupId(kafkaPropertyConfig.getConsumerGroup());
        return repliesContainer;
    }

    @Bean
    public KafkaAdmin.NewTopics requestTopics() {
        List<NewTopic> topicList = getAllTopicNames(false)
                .stream().map(topicNane -> TopicBuilder.name(topicNane)
                        .partitions(kafkaPropertyConfig.getTopics().getPartitionsCount())
                        .replicas(kafkaPropertyConfig.getTopics().getReplicasCount()).build()).toList();
        NewTopic[] topicArray = new NewTopic[topicList.size()];
        topicArray = topicList.toArray(topicArray);
        return new KafkaAdmin.NewTopics(topicArray);
    }

    @Bean
    public KafkaAdmin.NewTopics replyTopics() {
        List<NewTopic> topicList = getAllTopicNames(true)
                .stream().map(topicNane -> TopicBuilder.name(topicNane)
                        .partitions(kafkaPropertyConfig.getTopics().getPartitionsCount())
                        .replicas(kafkaPropertyConfig.getTopics().getReplicasCount()).build()).toList();
        NewTopic[] topicArray = new NewTopic[topicList.size()];
        topicArray = topicList.toArray(topicArray);
        return new KafkaAdmin.NewTopics(topicArray);
    }

    @Bean
    public DefaultErrorHandler errorHandler() {
        BackOff fixedBackOff = new FixedBackOff(
                kafkaPropertyConfig.getBackoffInterval().toMillis(),
                kafkaPropertyConfig.getBackoffMaxFailure());
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                (consumerRecord, exception) -> {
                    log.error("Message failed after retries: {}, exception: {}", consumerRecord.value(), exception.getMessage(), exception);
                }, fixedBackOff, new BackOffHandler() {
        });
        errorHandler.setRetryListeners((record, ex, deliveryAttempt) -> {
            log.warn("Retry attempt {} for record: {}", deliveryAttempt, record.value());
        });
        errorHandler.setSeekAfterError(true);
        errorHandler.addRetryableExceptions(SocketTimeoutException.class);
        errorHandler.addNotRetryableExceptions(IllegalArgumentException.class);
        errorHandler.addNotRetryableExceptions(RecordDeserializationException.class);
        return errorHandler;
    }

    public List<String> getAllTopicNames(boolean isReply) {
        return providers.stream().flatMap(
                provider -> provider.getProviderApis().stream()
                        .map(api -> getTopicName(api, isReply))).toList();
    }

    public String getTopicName(ProviderApi api, boolean isReply) {
        if (isReply) {
            return createTopicName(getPrefix(api), api.getBusinessMethod(), getSuffix(true));
        } else {
            return createTopicName(getPrefix(api), api.getBusinessMethod(), getSuffix(false));
        }
    }

    public String getAsyncTopicName(BusinessMethodEnum businessMethod, boolean isReply) {
        if (isReply) {
            return createTopicName(getPrefix(null), businessMethod, getSuffix(true));
        } else {
            return createTopicName(getPrefix(null), businessMethod, getSuffix(false));
        }
    }

    private String getSuffix(boolean isReply) {
        if (isReply) {
            return kafkaPropertyConfig.getTopics().getReplyTopicSuffix();
        } else {
            return kafkaPropertyConfig.getTopics().getRequestTopicSuffix();
        }
    }

    private String getPrefix(ProviderApi api) {
        if (api == null || kafkaPropertyConfig.isAsyncModeEnabled()) {
            return kafkaPropertyConfig.getAsyncMode().getTopicPrefix();
        } else {
            return api.getProvider().getPropertyName();
        }
    }

    private String createTopicName(String prefix, BusinessMethodEnum businessMethod, String directionSuffix) {
        StringBuilder builder = new StringBuilder();
        if (prefix != null && !prefix.isEmpty()) {
            builder.append(prefix.toLowerCase())
                    .append("_");
        }
        builder.append(businessMethod.name().toLowerCase());
        if (directionSuffix != null && !directionSuffix.isEmpty()) {
            builder.append("_").append(directionSuffix.toLowerCase());
        }
        return builder.toString();
    }

}
