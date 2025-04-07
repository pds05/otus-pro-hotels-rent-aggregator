package ru.otus.java.pro.result.project.messageprocessor.messaging;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import ru.otus.java.pro.result.project.messageprocessor.entities.Provider;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
public class MessageTopicFilter implements RecordFilterStrategy<String, Object> {

    private final List<Provider> providers;

    @Override
    public boolean filter(ConsumerRecord<String, Object> consumerRecord) {
        return providers.stream().noneMatch(provider -> provider.getPropertyName().equalsIgnoreCase(new String(consumerRecord.headers().lastHeader(KafkaConsumerService.KAFKA_PROVIDER_HEADER).value(), StandardCharsets.UTF_8)));
    }

//    @Override
//    public List<ConsumerRecord<String, String>> filterBatch(List<ConsumerRecord<String, String>> records) {
//        return records.stream().filter(record ->
//                providers.stream().anyMatch(provider ->
//                        provider.getPropertyName().equalsIgnoreCase(
//                                new String(record.headers().lastHeader(KafkaConsumerService.KAFKA_PROVIDER_HEADER).value(),
//                                        StandardCharsets.UTF_8)))).toList();
//
//    }

}
