package ru.otus.java.pro.result.project.messageshifter.services;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.messageshifter.configs.RestClientConfig;
import ru.otus.java.pro.result.project.messageshifter.dtos.HotelDto;
import ru.otus.java.pro.result.project.messageshifter.dtos.HotelDtoRq;
import ru.otus.java.pro.result.project.messageshifter.integrations.RestClientService;

import java.util.Map;

@Service
@AllArgsConstructor
public class KafkaConsumerService {
    public static final String KAFKA_SERVICE_HEADER = "kafka_serviceName";
    private final RestClientConfig restClientConfig;

    @KafkaListener(topics = "requestTopic")
    @SendTo
    public HotelDto listen(HotelDtoRq request,  @Headers Map<String, Object> headers) {
        System.out.println("Kafka listener received: " + request);
        RestClientService restClientService = new RestClientService(headers.get(KAFKA_SERVICE_HEADER).toString(), restClientConfig);
        re
        return in.toUpperCase();
    }
}
