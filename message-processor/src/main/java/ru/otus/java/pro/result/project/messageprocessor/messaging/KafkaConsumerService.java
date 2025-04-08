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
import ru.otus.java.pro.result.project.messageprocessor.dtos.messages.*;
import ru.otus.java.pro.result.project.messageprocessor.dtos.providers.*;
import ru.otus.java.pro.result.project.messageprocessor.entities.Provider;
import ru.otus.java.pro.result.project.messageprocessor.entities.ProviderApi;
import ru.otus.java.pro.result.project.messageprocessor.exceptions.ApplicationException;
import ru.otus.java.pro.result.project.messageprocessor.integrations.RestService;
import ru.otus.java.pro.result.project.messageprocessor.utils.ApplicationUtil;

import java.util.Base64;
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
    private final MessageMapper messageMapper;

    //TODO Реализовать динамическое создание бинов KafkaListener для асинхронного режима
    // в текущей версии действует ограничение один топик = один KafkaListener = один сервис
    // Важно: для асинхронного режима в проперти должна быть включена интеграция только одного сервиса

    @KafkaListener(
            topics = "#{kafkaConfig.getRequestTopics('FIND_HOTELS_WITH_FILTER', 'FIND_ALL_HOTELS_IN_CITY')}",
            containerFactory = "kafkaListenerFactory",
            concurrency = "${spring.kafka.topic-listener-threads}",
            filter = "acceptEnableProviderMessageFilter"
    )
    @SendTo
    public Message<HotelsDtoMsg> processFindHotel(
            HotelDtoRqMsg request,
            @Header(name = KAFKA_PROVIDER_HEADER, required = false) String providerName,
            @Header(name = KAFKA_BUSINESS_METHOD_HEADER) String method,
            @Header(name = KafkaHeaders.RECEIVED_TOPIC) String requestTopic,
            @Header(name = KafkaHeaders.REPLY_TOPIC) String replyTopic) {

        logRequestMessage(requestTopic, method, providerName, request);
        //INFO: если включено несколько сервисов в асинхронном режиме, то работать будет первый из списка
        if (kafkaPropertyConfig.isAsyncModeEnabled()) {
            providerName = providers.stream().findFirst().orElseThrow(() -> new ApplicationException("Received message but not available providers for processing")).getPropertyName();
        }
        Provider provider = ApplicationUtil.getProvider(providerName);
        ProviderApi api = provider.getProviderApis().stream().filter(a -> a.getBusinessMethod().name().equalsIgnoreCase(method)).findFirst().orElseThrow(() -> new ApplicationException("Business method '" + method + "' not found"));

        SomeServiceHotelDtoRq providerRequest = messageMapper.toProviderDto(request, SomeServiceHotelDtoRq.class);
        List<SomeServiceHotelDto> providerResponse = restService.getAsList(api, providerRequest);
        List<HotelDtoMsg> providerResp = messageMapper.toInternalDto(providerResponse, HotelDtoMsg.class);

        logReplyMessage(replyTopic, method, providerName, providerResp);
        return MessageBuilder.withPayload(new HotelsDtoMsg(providerResp))
                .setHeader(KAFKA_PROVIDER_HEADER, providerName)
                .build();
    }

    @KafkaListener(
            topics = "#{kafkaConfig.getRequestTopics('CREATE_ORDER')}",
            containerFactory = "kafkaListenerFactory",
            concurrency = "${spring.kafka.topic-listener-threads}",
            filter = "acceptEnableProviderMessageFilter"
    )
    @SendTo
    public Message<UserOrderDtoMsg> processCreateOrder(
            UserOrderCreateDtoRqMsg request,
            @Header(name = KAFKA_PROVIDER_HEADER, required = false) String providerName,
            @Header(name = KAFKA_BUSINESS_METHOD_HEADER) String method,
            @Header(name = KafkaHeaders.RECEIVED_TOPIC) String requestTopic,
            @Header(name = KafkaHeaders.REPLY_TOPIC) String replyTopic) {

        logRequestMessage(requestTopic, method, providerName, request);
        //INFO: если включено несколько сервисов в асинхронном режиме, то работать будет первый из списка
        if (kafkaPropertyConfig.isAsyncModeEnabled()) {
            providerName = providers.stream().findFirst().orElseThrow(() -> new ApplicationException("Received message but not available providers for processing")).getPropertyName();
        }
        Provider provider = ApplicationUtil.getProvider(providerName);
        ProviderApi api = provider.getProviderApis().stream().filter(a -> a.getBusinessMethod().name().equalsIgnoreCase(method)).findFirst().orElseThrow(() -> new ApplicationException("Business method '" + method + "' not found"));

        SomeServiceUserOrderCreateDtoRq providerRequest = messageMapper.toProviderDto(request, SomeServiceUserOrderCreateDtoRq.class);
        SomeServiceUserOrderDto providerResponse = restService.post(api, providerRequest, SomeServiceUserOrderDto.class);
        UserOrderDtoMsg reply = messageMapper.toInternalDto(providerResponse, UserOrderDtoMsg.class);

        logReplyMessage(replyTopic, method, providerName, reply);
        return MessageBuilder.withPayload(reply)
                .setHeader(KAFKA_PROVIDER_HEADER, providerName)
                .build();
    }

    @KafkaListener(
            topics = "#{kafkaConfig.getRequestTopics('CANCEL_ORDER')}",
            containerFactory = "kafkaListenerFactory",
            concurrency = "${spring.kafka.topic-listener-threads}",
            filter = "acceptEnableProviderMessageFilter"
    )
    @SendTo
    public Message<UserOrderDtoMsg> processCancelOrder(
            UserOrderCancelDtoRqMsg request,
            @Header(name = KAFKA_PROVIDER_HEADER, required = false) String providerName,
            @Header(name = KAFKA_BUSINESS_METHOD_HEADER) String method,
            @Header(name = KafkaHeaders.RECEIVED_TOPIC) String requestTopic,
            @Header(name = KafkaHeaders.REPLY_TOPIC) String replyTopic) {

        logRequestMessage(requestTopic, method, providerName, request);
        //INFO: если включено несколько сервисов в асинхронном режиме, то работать будет первый из списка
        if (kafkaPropertyConfig.isAsyncModeEnabled()) {
            providerName = providers.stream().findFirst().orElseThrow(() -> new ApplicationException("Received message but not available providers for processing")).getPropertyName();
        }
        Provider provider = ApplicationUtil.getProvider(providerName);
        ProviderApi api = provider.getProviderApis().stream().filter(a -> a.getBusinessMethod().name().equalsIgnoreCase(method)).findFirst().orElseThrow(() -> new ApplicationException("Business method '" + method + "' not found"));

        SomeServiceUserOrderCancelDtoRq providerRequest = messageMapper.toProviderDto(request, SomeServiceUserOrderCancelDtoRq.class);
        SomeServiceUserOrderDto providerResponse = restService.get(api, providerRequest, SomeServiceUserOrderDto.class);
        UserOrderDtoMsg reply = messageMapper.toInternalDto(providerResponse, UserOrderDtoMsg.class);

        logReplyMessage(replyTopic, method, providerName, reply);
        return MessageBuilder.withPayload(reply)
                .setHeader(KAFKA_PROVIDER_HEADER, providerName)
                .build();
    }

    @KafkaListener(
            topics = "#{kafkaConfig.getRequestTopics('REGISTER_USER')}",
            containerFactory = "kafkaListenerFactory",
            concurrency = "${spring.kafka.topic-listener-threads}",
            filter = "acceptEnableProviderMessageFilter"
    )
    @SendTo
    public Message<UserDtoMsg> processRegisterUser(
            UserDtoRqMsg request,
            @Header(name = KAFKA_PROVIDER_HEADER, required = false) String providerName,
            @Header(name = KAFKA_BUSINESS_METHOD_HEADER) String method,
            @Header(name = KafkaHeaders.RECEIVED_TOPIC) String requestTopic,
            @Header(name = KafkaHeaders.REPLY_TOPIC) String replyTopic) {

        logRequestMessage(requestTopic, method, providerName, request);
        //INFO: если включено несколько сервисов в асинхронном режиме, то работать будет первый из списка
        if (kafkaPropertyConfig.isAsyncModeEnabled()) {
            providerName = providers.stream().findFirst().orElseThrow(() -> new ApplicationException("Received message but not available providers for processing")).getPropertyName();
        }
        Provider provider = ApplicationUtil.getProvider(providerName);
        ProviderApi api = provider.getProviderApis().stream().filter(a -> a.getBusinessMethod().name().equalsIgnoreCase(method)).findFirst().orElseThrow(() -> new ApplicationException("Business method '" + method + "' not found"));
        //Декодируем пароль перед отправкой в сервис, предполагается передача rest-запроса по https
        String decodedPassword = new String(Base64.getDecoder().decode(request.getPassword()));
        request.setPassword(decodedPassword);

        SomeServiceUserDtoRq providerRequest = messageMapper.toProviderDto(request, SomeServiceUserDtoRq.class);
        SomeServiceUserDto providerResponse = restService.post(api, providerRequest, SomeServiceUserDto.class);
        UserDtoMsg reply = messageMapper.toInternalDto(providerResponse, UserDtoMsg.class);

        logReplyMessage(replyTopic, method, providerName, reply);
        return MessageBuilder.withPayload(reply)
                .setHeader(KAFKA_PROVIDER_HEADER, providerName)
                .build();
    }

    private void logRequestMessage(String requestTopic, String method, String providerName, Object rqDto) {
        if (log.isDebugEnabled()) {
            log.debug("Message received, requestTopic={}, method={}, providerName={}", requestTopic, method, providerName);
        }
        if (log.isTraceEnabled()) {
            log.trace("Message received, requestTopic={}, method={}, providerName={}, data={}", requestTopic, method, providerName, rqDto);
        }
    }

    private void logReplyMessage(String replyTopic, String method, String providerName, Object replyDto) {
        if (log.isDebugEnabled()) {
            log.debug("Replying message, replyTopic={}, method={}, providerName={}", replyTopic, method, providerName);
        }
        if (log.isTraceEnabled()) {
            log.trace("Replying message, replyTopic={}, method={}, providerName={}, data={}", replyTopic, method, providerName, replyDto);
        }
    }
}
