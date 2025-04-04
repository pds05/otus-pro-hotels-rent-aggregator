package ru.otus.java.pro.result.project.hotelsaggregator.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.requestreply.AggregatingReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotelsaggregator.configs.KafkaConfig;
import ru.otus.java.pro.result.project.hotelsaggregator.configs.properties.KafkaPropertyConfig;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.messages.ProviderResponseDto;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.Provider;
import ru.otus.java.pro.result.project.hotelsaggregator.enums.BusinessMethodEnum;
import ru.otus.java.pro.result.project.hotelsaggregator.exceptions.ApplicationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static ru.otus.java.pro.result.project.hotelsaggregator.messaging.KafkaReplyProducerService.KAFKA_BUSINESS_METHOD;

@Slf4j
@ConditionalOnBean(name = "kafkaAggregatingTemplate")
@RequiredArgsConstructor
@Service
public class KafkaAsyncProducerService implements MessageService {

    private final AggregatingReplyingKafkaTemplate<String, Object, Object> kafkaAggregatingTemplate;
    private final KafkaPropertyConfig kafkaPropertyConfig;
    private final KafkaConfig kafkaConfig;

    @Override
    public <T> List<ProviderResponseDto<T>> sendMessage(Object message, BusinessMethodEnum businessMethod, Class<?> clazz) {
        List<ProviderResponseDto<T>> responseMessages = new ArrayList<>();
        try {
            if (!kafkaAggregatingTemplate.waitForAssignment(kafkaPropertyConfig.getReplyContainerInitTimeout())) {
                throw new ApplicationException("Reply container did not initialize");
            }
            String requestTopic = kafkaConfig.getAsyncTopicName(businessMethod, false);
            String replyTopic = kafkaConfig.getAsyncTopicName(businessMethod, true);

            ProducerRecord<String, Object> record = new ProducerRecord<>(requestTopic, message);
            record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, replyTopic.getBytes()));
            record.headers().add(KAFKA_BUSINESS_METHOD, businessMethod.name().getBytes());

            if (log.isDebugEnabled()) log.debug("Sending message in topic={}", requestTopic);
            if (log.isTraceEnabled()) log.trace("Sending message in topic={}, record={}", requestTopic, record);
            RequestReplyFuture<String, Object, Collection<ConsumerRecord<String, Object>>> future =
                    kafkaAggregatingTemplate.sendAndReceive(record);
            ConsumerRecord<String, Collection<ConsumerRecord<String, Object>>> proxyConsumerRecord = null;
            try {
                proxyConsumerRecord = future.get(kafkaPropertyConfig.getAsyncMode().getReplyTimeout().getSeconds(), TimeUnit.SECONDS);
                List<String> providersReplied = proxyConsumerRecord.value().stream().map(proxyRecord -> ((ProviderResponseDto<T>) proxyRecord.value()).getProviderName()).toList();
                if (log.isDebugEnabled())
                    log.debug("Reply message, topic={}, providers={}", proxyConsumerRecord.topic(), providersReplied);
                if (log.isTraceEnabled())
                    log.trace("Reply message, topic={}, providers={}, data={}", proxyConsumerRecord.topic(), providersReplied, proxyConsumerRecord);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                log.warn("The waiting replies time is over, topic={}", requestTopic, e);
            }
            Optional.ofNullable(proxyConsumerRecord).ifPresent(proxy -> {
                proxy.value().forEach(consumerResponse -> responseMessages.add((ProviderResponseDto<T>) consumerResponse.value()));
            });
        } catch (InterruptedException exception) {
            log.error("Kafka broker access error", exception);
            throw new ApplicationException("Unexpected error request processing");
        }
        return responseMessages;
    }

    @Override
    public <T> ProviderResponseDto<T> sendMessage(Provider provider, Object message, BusinessMethodEnum method, Class<?> clazz) {
        return null;
    }
}
