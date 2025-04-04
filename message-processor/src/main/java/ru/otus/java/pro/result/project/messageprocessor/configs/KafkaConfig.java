package ru.otus.java.pro.result.project.messageprocessor.configs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.errors.RecordDeserializationException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;
import ru.otus.java.pro.result.project.messageprocessor.configs.properties.KafkaPropertyConfig;
import ru.otus.java.pro.result.project.messageprocessor.entities.Provider;
import ru.otus.java.pro.result.project.messageprocessor.entities.ProviderApi;
import ru.otus.java.pro.result.project.messageprocessor.enums.BusinessMethodEnum;

import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableKafka
public class KafkaConfig {
    private final List<Provider> providers;
    private final KafkaPropertyConfig kafkaPropertyConfig;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerFactory(
            ConsumerFactory<String, Object> consumerFactory,
            KafkaTemplate<String, Object> replyTemplate,
            DefaultErrorHandler errorHandler) {

        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setReplyTemplate(replyTemplate);
        factory.setCommonErrorHandler(errorHandler);
//        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaPropertyConfig.getBrokers());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaPropertyConfig.getConsumerGroup());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        config.put(JsonDeserializer.TYPE_MAPPINGS, "hotelDtoRq:ru.otus.java.pro.result.project.messageprocessor.dtos.HotelDtoRq");
        if (kafkaPropertyConfig.isAsyncModeEnabled()) {
            log.info("Kafka running in aggregate replies mode (async): one request per topic");
        } else {
            log.info("Kafka running in sync request/reply mode: one request per multi topics");
        }
        return new DefaultKafkaConsumerFactory<>(config);
    }

    public Map<String, Object> producerConfigs() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaPropertyConfig.getBrokers());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(JsonSerializer.TYPE_MAPPINGS, "providerResponseDto:ru.otus.java.pro.result.project.messageprocessor.dtos.ProviderResponseDto, " +
                "hotelDto:ru.otus.java.pro.result.project.messageprocessor.dtos.HotelDto");
        return config;
    }

    @Bean
    public ProducerFactory<String, Object> replyProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Object> replyTemplate(
            ProducerFactory<String, Object> replyProducerFactory) {
        return new KafkaTemplate<>(replyProducerFactory);
    }

    @Bean
    public DefaultErrorHandler errorHandler() {
        BackOff fixedBackOff = new FixedBackOff(
                kafkaPropertyConfig.getBackoffInterval().toMillis(),
                kafkaPropertyConfig.getBackoffMaxFailure());
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                (consumerRecord, exception) -> {
                    log.error("Message failed after retries: {}, exception: {}", consumerRecord.value(), exception.getMessage(), exception);
                }, fixedBackOff);
        errorHandler.addRetryableExceptions(SocketTimeoutException.class);
        errorHandler.addNotRetryableExceptions(NullPointerException.class);
        errorHandler.addNotRetryableExceptions(IllegalArgumentException.class);
        errorHandler.addNotRetryableExceptions(RecordDeserializationException.class);
        errorHandler.setRetryListeners((record, ex, deliveryAttempt) -> {
            log.warn("Retry attempt {} for record: {}", deliveryAttempt, record.value());
        });
        return errorHandler;
    }

    public List<String> getAllRequestTopics() {
        return getAllTopicNames(false);
    }

    public List<String> getRequestTopics(String... businessMethod) {
        return providers.stream()
                .flatMap(provider -> provider.getProviderApis().stream()
                        .filter(api -> Arrays.stream(businessMethod).anyMatch(method -> api.getBusinessMethod().name().equalsIgnoreCase(method)))
                        .map(api -> getTopicName(api, false)))
                .toList();
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
            return kafkaPropertyConfig.getReplyTopicSuffix();
        } else {
            return kafkaPropertyConfig.getRequestTopicSuffix();
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
