package ru.otus.java.pro.result.project.messageprocessor.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.messageprocessor.configs.properties.KafkaPropertyConfig;
import ru.otus.java.pro.result.project.messageprocessor.dtos.HotelDto;
import ru.otus.java.pro.result.project.messageprocessor.dtos.HotelDtoRq;
import ru.otus.java.pro.result.project.messageprocessor.dtos.ProviderResponseDto;
import ru.otus.java.pro.result.project.messageprocessor.entities.Provider;
import ru.otus.java.pro.result.project.messageprocessor.entities.ProviderApi;
import ru.otus.java.pro.result.project.messageprocessor.exceptions.ApplicationException;
import ru.otus.java.pro.result.project.messageprocessor.integrations.RestService;
import ru.otus.java.pro.result.project.messageprocessor.utils.ApplicationUtil;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    public static final String KAFKA_PROVIDER_HEADER = "kafka_providerName";
    public static final String KAFKA_BUSINESS_METHOD_HEADER = "kafka_businessMethod";

    private final RestService restService;
    private final KafkaPropertyConfig kafkaPropertyConfig;
    private final List<Provider> providers;

    //TODO Реализовать динамическое создание бинов KafkaListener для асинхронного режима
    // в текущей версии действует ограничение один топик = один KafkaListener = один сервис
    // Важно: для асинхронного режима в проперти должна быть включена интеграция только одного сервиса

    @KafkaListener(
            topics = "#{kafkaConfig.getRequestTopics('FIND_HOTELS_WITH_FILTER', 'FIND_ALL_HOTELS_IN_CITY')}",
            containerFactory = "kafkaListenerFactory",
            concurrency = "${spring.kafka.listener-threads}"
    )
    @SendTo
    public Message<ProviderResponseDto> process(
            HotelDtoRq request,
            @Header(name = KAFKA_PROVIDER_HEADER, required = false) String providerName,
            @Header(name = KAFKA_BUSINESS_METHOD_HEADER) String method,
            @Header(name = KafkaHeaders.RECEIVED_TOPIC) String requestTopic,
            @Header(name = KafkaHeaders.REPLY_TOPIC) String replyTopic) {

        if (log.isDebugEnabled()) {
            log.debug("Message received, requestTopic={}, method={}, providerName={}", requestTopic, method, providerName);
        }
        if (log.isTraceEnabled()) {
            log.trace("Message received, requestTopic={}, method={}, providerName={}, data={}", requestTopic, method, providerName, request);
        }
        //INFO: если включено несколько сервисов в асинхронном режиме, то работать будет первый из списка
        if (kafkaPropertyConfig.isAsyncModeEnabled()) {
            providerName = providers.stream().findFirst().orElseThrow(() -> new ApplicationException("Received message but not available providers for processing")).getPropertyName();
        }
        Provider provider = ApplicationUtil.getProvider(providerName);
        ProviderApi api = provider.getProviderApis().stream().filter(a -> a.getBusinessMethod().name().equalsIgnoreCase(method)).findFirst().orElseThrow(() -> new ApplicationException("Business method '" + method + "' not found"));
        String restMethod = api.getRestMethod();
        List<HotelDto> response;
        if (restMethod.equals("GET")) {
            response = restService.getRequest(api, request);
        } else {
            throw new ApplicationException("Rest method " + method + " not supported");
        }
        ProviderResponseDto responseDto = new ProviderResponseDto();
        responseDto.setProviderName(providerName);
        responseDto.setData(response);
        if (log.isDebugEnabled()) {
            log.debug("Replying message, replyTopic={}, method={}, providerName={}", replyTopic, method, providerName);
        }
        if (log.isTraceEnabled()) {
            log.trace("Replying message, replyTopic={}, method={}, providerName={}, data={}", replyTopic, method, providerName, responseDto);
        }
        return MessageBuilder.withPayload(responseDto)
                .setHeader(KAFKA_PROVIDER_HEADER, providerName)
                .build();
    }
}
