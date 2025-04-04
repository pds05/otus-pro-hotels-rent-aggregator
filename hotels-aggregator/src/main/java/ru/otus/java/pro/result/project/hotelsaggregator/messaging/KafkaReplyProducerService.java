package ru.otus.java.pro.result.project.hotelsaggregator.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotelsaggregator.configs.KafkaConfig;
import ru.otus.java.pro.result.project.hotelsaggregator.configs.properties.KafkaPropertyConfig;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.internals.ProviderResponseDto;
import ru.otus.java.pro.result.project.hotelsaggregator.enums.BusinessMethodEnum;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.Provider;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.ProviderApi;
import ru.otus.java.pro.result.project.hotelsaggregator.exceptions.ApplicationException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@ConditionalOnBean(name = "kafkaReplyingTemplate")
@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaReplyProducerService implements MessageService {

    public static final String KAFKA_PROVIDER_HEADER = "kafka_providerName";
    public static final String KAFKA_BUSINESS_METHOD = "kafka_businessMethod";

    private final List<Provider> providers;
    private final KafkaPropertyConfig kafkaPropertyConfig;
    private final KafkaConfig kafkaConfig;
    private final ReplyingKafkaTemplate<String, Object, Object> kafkaReplyingTemplate;

    public <T> List<ProviderResponseDto<T>> sendMessage(Object message, BusinessMethodEnum method, Class<?> clazz) {
        List<ProviderResponseDto<T>> responseMessages = new ArrayList<>();
        try {
            if (!kafkaReplyingTemplate.waitForAssignment(kafkaPropertyConfig.getReplyContainerInitTimeout())) {
                throw new ApplicationException("Reply container did not initialize");
            }
            List<RequestReplyFuture<String, Object, Object>> replies = new ArrayList<>(providers.size());

            providers.forEach(provider -> {
                ProviderApi api = provider.getProviderApi(method);
                String requestTopic = kafkaConfig.getTopicName(api, false);
                String replyTopic = kafkaConfig.getTopicName(api, true);

                ProducerRecord<String, Object> record = new ProducerRecord<>(requestTopic, message);
                record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, replyTopic.getBytes()));
                record.headers().add(KAFKA_PROVIDER_HEADER, provider.getPropertyName().getBytes());
                record.headers().add(KAFKA_BUSINESS_METHOD, method.name().getBytes());

                if (log.isDebugEnabled()) log.debug("Sending message in topic={}", requestTopic);
                if (log.isTraceEnabled()) log.trace("Sending message in topic={}, record={}", requestTopic, record);
                RequestReplyFuture<String, Object, Object> replyFuture = kafkaReplyingTemplate.sendAndReceive(record);
                try {
                    SendResult<String, Object> sendResult = replyFuture.getSendFuture().get(kafkaPropertyConfig.getSendTimeout().getSeconds(), TimeUnit.SECONDS);
                    replies.add(replyFuture);
                    if (log.isDebugEnabled()) log.debug("Message delivered to consumer, topic={}", requestTopic);
                    if (log.isTraceEnabled())
                        log.trace("Message delivered to consumer, topic={}, record={}", requestTopic, sendResult.getRecordMetadata());
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    log.warn("Transmission error: consumer not confirm the message, topic={}", requestTopic, e);
                }
            });
            replies.forEach(reply -> {
                ConsumerRecord<String, Object> consumerRecord = null;
                try {
                    consumerRecord = reply.get(kafkaPropertyConfig.getReplyTimeout().getSeconds(), TimeUnit.SECONDS);
                    ProviderResponseDto<T> consumerResponse = (ProviderResponseDto<T>) consumerRecord.value();
                    responseMessages.add(consumerResponse);
                    if (log.isDebugEnabled())
                        log.debug("Reply message, topic={}, provider={}", consumerRecord.topic(), consumerResponse.getProviderName());
                    if (log.isTraceEnabled())
                        log.trace("Reply message, topic={}, provider={}, data={}", consumerRecord.topic(), consumerResponse.getProviderName(), consumerResponse.getData());
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    log.error("Consumer not reply to the message", e);
                }
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
