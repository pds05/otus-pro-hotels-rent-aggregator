package ru.otus.java.pro.result.project.hotelsaggregator.configs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.*;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.Provider;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

import java.net.SocketTimeoutException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableKafka
public class KafkaConfig {

    private final KafkaTopicsPropertyConfig kafkaProperties;
    private final List<Provider> providers;

    @Value("${spring.kafka.consumer-group}")
    private String consumerGroup;

    @Value(value = "${spring.kafka.backoff.interval}")
    private Long interval;

    @Value(value = "${spring.kafka.backoff.max-failure}")
    private Long maxAttempts;

    @Value(value = "${spring.kafka.producer.send-timeout}")
    private Duration sendTimeout;

    @Value(value = "${spring.kafka.producer.send-timeout}")
    private Duration replyTimeout;

    @Value(value = "${spring.kafka.consumer.reply-container-init-timeout}")
    private Duration replyContainerInitTimeout;

    @Bean
    public ReplyingKafkaTemplate<String, Object, Object> replyingTemplate(
            ProducerFactory<String, Object> pf,
            ConcurrentMessageListenerContainer<String, Object> repliesContainer) {
        ReplyingKafkaTemplate<String, Object, Object> replyingTemplate = new ReplyingKafkaTemplate<>(pf, repliesContainer);
        replyingTemplate.setConsumerFactory(consumerFactory());
        return replyingTemplate;
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, Object> repliesContainer(
            ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory) {

        List<String> listReplyTopics = providers.stream().flatMap(provider -> provider.getProviderApis().stream().map(api -> api.createTopicName(kafkaProperties.getReplyTopicSuffix()))).toList();
        String[] arrReplyTopics = new String[listReplyTopics.size()];
        arrReplyTopics = listReplyTopics.toArray(arrReplyTopics);
        ConcurrentMessageListenerContainer<String, Object> repliesContainer =
                containerFactory.createContainer(arrReplyTopics);
        repliesContainer.getContainerProperties().setGroupId(consumerGroup);
        repliesContainer.setAutoStartup(false);
        containerFactory.setConsumerFactory(consumerFactory());
        return repliesContainer;
    }

    @Bean
    public KafkaAdmin.NewTopics requestTopics() {
        List<NewTopic> topicList = providers.stream().flatMap(
                        provider -> provider.getProviderApis().stream().map(
                                api -> TopicBuilder.name(api.createTopicName(kafkaProperties.getRequestTopicSuffix()))
                                        .partitions(kafkaProperties.getPartitionsCount())
                                        .replicas(kafkaProperties.getReplicasCount())
                                        .build()))
                .toList();
        NewTopic[] topicArray = new NewTopic[topicList.size()];
        topicArray = topicList.toArray(topicArray);
        return new KafkaAdmin.NewTopics(topicArray);
    }

    @Bean
    public KafkaAdmin.NewTopics replyTopics() {
        List<NewTopic> topicList = providers.stream().flatMap(
                        provider -> provider.getProviderApis().stream().map(
                                api -> TopicBuilder.name(api.createTopicName(kafkaProperties.getReplyTopicSuffix()))
                                        .partitions(kafkaProperties.getPartitionsCount())
                                        .replicas(kafkaProperties.getReplicasCount())
                                        .build()))
                .toList();
        NewTopic[] topicArray = new NewTopic[topicList.size()];
        topicArray = topicList.toArray(topicArray);
        return new KafkaAdmin.NewTopics(topicArray);
    }

    @Bean
    public DefaultErrorHandler errorHandler() {
        BackOff fixedBackOff = new FixedBackOff(interval, maxAttempts);
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                (consumerRecord, exception) -> {
                    log.error("Message failed after retries: {}, exception: {}", consumerRecord.value(), exception.getMessage(), exception);
                }, fixedBackOff);
        errorHandler.setRetryListeners((record, ex, deliveryAttempt) -> {
            log.warn("Retry attempt {} for record: {}", deliveryAttempt, record.value());
        });
        errorHandler.addRetryableExceptions(SocketTimeoutException.class);
        errorHandler.addNotRetryableExceptions(NullPointerException.class);
        return errorHandler;
    }

    @Bean
    public <V> ConsumerFactory<String, V> consumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);

        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "ru.otus.java.pro.result.project.hotelsaggregator.dtos.HotelDto");
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "ru.otus.java.pro.result.project.hotelsaggregator.dtos");
        return new DefaultKafkaConsumerFactory<>(config);
    }

}
