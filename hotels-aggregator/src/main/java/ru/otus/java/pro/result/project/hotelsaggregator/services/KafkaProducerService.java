package ru.otus.java.pro.result.project.hotelsaggregator.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotelsaggregator.configs.KafkaConfig;
import ru.otus.java.pro.result.project.hotelsaggregator.configs.KafkaTopicsPropertyConfig;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.HotelDtos;
import ru.otus.java.pro.result.project.hotelsaggregator.enums.BusinessMethodEnum;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.Provider;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.ProviderApi;
import ru.otus.java.pro.result.project.hotelsaggregator.exceptions.ApplicationException;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducerService {
    public static final String KAFKA_PROVIDER_HEADER = "kafka_providerName";
    public static final String KAFKA_BUSINESS_METHOD = "kafka_businessMethod";
    private final KafkaTopicsPropertyConfig kafkaTopicsPropertyConfig;
    private final KafkaConfig kafkaConfig;
    private final List<Provider> providers;
//    private final ObjectMapper objectMapper;

    private final ReplyingKafkaTemplate<String, Object, Object> requestReplyKafkaTemplate;

    @SneakyThrows
    public <T> T sendMessage(Object message, BusinessMethodEnum method, Class<T> responseClass) {
        if (!requestReplyKafkaTemplate.waitForAssignment(kafkaConfig.getReplyContainerInitTimeout())) {
            throw new ApplicationException("Reply container did not initialize");
        }
        ProviderApi api = providers.get(0).getProviderApi(method);
        ProducerRecord<String, Object> record = new ProducerRecord<>(api.createTopicName(kafkaTopicsPropertyConfig.getRequestTopicSuffix()), message);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, (api.createTopicName(kafkaTopicsPropertyConfig.getReplyTopicSuffix())).getBytes()));
        record.headers().add(KAFKA_PROVIDER_HEADER, providers.get(0).getPropertyName().getBytes());
        record.headers().add(KAFKA_BUSINESS_METHOD, method.name().getBytes());
        record.headers().add(JsonDeserializer.TYPE_MAPPINGS, HotelDtos.class.getTypeName().getBytes());
        RequestReplyFuture<String, Object, Object> replyFuture = requestReplyKafkaTemplate.sendAndReceive(record);
        SendResult<String, Object> sendResult = replyFuture.getSendFuture().get(kafkaConfig.getSendTimeout().getSeconds(), TimeUnit.SECONDS);

        log.debug("Message status: {}", sendResult.getRecordMetadata());
        ConsumerRecord<String, Object> consumerRecord = replyFuture.get(kafkaConfig.getReplyTimeout().getSeconds(), TimeUnit.SECONDS);
        //Если не отправлять потребителю заголовок 'spring.json.type.mapping' с типом объекта для десериализации ответа
        //Или потребитель не возвращает заголовок '__TypeId__' с переложенным значением из полученного в запросе заголовка 'spring.json.type.mapping'
        //Тогда десериализуем объект из строки с помощью objectMapper и указываем целевой тип
        //ИЛИ же обеспечиваем одинаковый тип сообщения в поставщике и потребителе, тогда выполняется нативное преобразование на основе заголовка '__TypeId__'
        //        HotelDtos resp = objectMapper.readValue(consumerRecord.value(), HotelDtos.class);
        log.debug("Message result: {}", consumerRecord.value());
        return (T) consumerRecord.value();
    }



}
