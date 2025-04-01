package ru.otus.java.pro.result.project.messageprocessor.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.messageprocessor.dtos.HotelDto;
import ru.otus.java.pro.result.project.messageprocessor.dtos.HotelDtoRq;
import ru.otus.java.pro.result.project.messageprocessor.dtos.HotelsDto;
import ru.otus.java.pro.result.project.messageprocessor.entities.Provider;
import ru.otus.java.pro.result.project.messageprocessor.entities.ProviderApi;
import ru.otus.java.pro.result.project.messageprocessor.exceptions.ApplicationException;
import ru.otus.java.pro.result.project.messageprocessor.integrations.RestClientService;
import ru.otus.java.pro.result.project.messageprocessor.utils.ApplicationUtil;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    public static final String KAFKA_PROVIDER_HEADER = "kafka_providerName";
    public static final String KAFKA_BUSINESS_METHOD_HEADER = "kafka_businessMethod";
    public static final String KAFKA_TYPE_ID_HEADER = "__TypeId__";

    private final RestClientService restClientService;

    @KafkaListener(
            topics = "#{kafkaConfiguration.dynamicTopics('FIND_HOTELS_WITH_FILTER', 'FIND_ALL_HOTELS_IN_CITY')}",
            containerFactory = "kafkaListenerFactory",
            properties = {"spring.json.value.default.type=ru.otus.java.pro.result.project.messageprocessor.dtos.HotelDtoRq"}
    )
    @SendTo
    public Message<HotelsDto> process(
            HotelDtoRq request,
            @Header(name = JsonDeserializer.TYPE_MAPPINGS) String mappingType,
            @Header(name = KAFKA_BUSINESS_METHOD_HEADER) String method,
            @Header(name = KAFKA_PROVIDER_HEADER) String providerName) {
        log.info("Request received {}, headers: method={}, providerName={}", request, method, providerName);
        Provider provider = ApplicationUtil.getProvider(providerName);
        ProviderApi api = provider.getProviderApis().stream().filter(a -> a.getBusinessMethod().name().equalsIgnoreCase(method)).findFirst().orElseThrow(() -> new ApplicationException("Business method '" + method + "' not found"));
        String restMethod = api.getRestMethod();
        List<HotelDto> response;
        if (restMethod.equals("GET")) {
            response = restClientService.getRequest(api, request, HotelDto.class);
        } else {
            throw new ApplicationException("Rest method " + method + " not supported");
        }
        return MessageBuilder.withPayload(new HotelsDto(response))
                .setHeader(KAFKA_TYPE_ID_HEADER, mappingType)
                .build();
    }
}
