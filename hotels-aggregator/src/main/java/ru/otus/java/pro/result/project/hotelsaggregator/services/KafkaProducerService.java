package ru.otus.java.pro.result.project.hotelsaggregator.services;

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
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotelsaggregator.configs.ApplicationConfig;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.HotelDto;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.HotelDtoRq;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.BusinessMethodEnum;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.Provider;
import ru.otus.java.pro.result.project.hotelsaggregator.exceptions.ApplicationException;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducerService {
    public static final String KAFKA_SERVICE_HEADER = "kafka_serviceName";
    public static final String KAFKA_SERVICE_URI = "kafka_serviceUri";
    private final ApplicationConfig applicationConfig;
    private final List<Provider> providers;

    private final ReplyingKafkaTemplate<String, HotelDtoRq, List<HotelDto>> replyingTemplate;

    @SneakyThrows
    public List<HotelDto> sendMessage(HotelDtoRq hotelDtoRq, BusinessMethodEnum method) {
        if (!replyingTemplate.waitForAssignment(Duration.ofSeconds(10))) {
            throw new ApplicationException("Reply container did not initialize");
        }
        ProducerRecord<String, HotelDtoRq> record = new ProducerRecord<>(providers.get(0).getPropertyName() + applicationConfig.getRequestTopicSuffix(), hotelDtoRq);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, (providers.get(0).getPropertyName() + applicationConfig.getReplyTopicSuffix()).getBytes()));
        record.headers().add(KAFKA_SERVICE_HEADER, providers.get(0).getPropertyName().getBytes());
        record.headers().add(KAFKA_SERVICE_URI, method.name().getBytes());

        RequestReplyFuture<String, HotelDtoRq, List<HotelDto>> replyFuture = replyingTemplate.sendAndReceive(record);
        SendResult<String, HotelDtoRq> sendResult = replyFuture.getSendFuture().get(10, TimeUnit.SECONDS);
        log.debug("Sent search hotel message: {}", sendResult.getRecordMetadata());
        ConsumerRecord<String, List<HotelDto>> consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);
        log.debug("Return search result: {}", consumerRecord.value());
        return consumerRecord.value();
    }



}
