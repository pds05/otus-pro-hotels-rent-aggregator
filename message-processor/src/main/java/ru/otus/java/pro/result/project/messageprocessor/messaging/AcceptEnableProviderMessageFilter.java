package ru.otus.java.pro.result.project.messageprocessor.messaging;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.stereotype.Component;
import ru.otus.java.pro.result.project.messageprocessor.configs.properties.KafkaPropertyConfig;
import ru.otus.java.pro.result.project.messageprocessor.entities.Provider;

import java.util.List;

import static ru.otus.java.pro.result.project.messageprocessor.messaging.KafkaConsumerService.*;

@RequiredArgsConstructor
@Component
public class AcceptEnableProviderMessageFilter implements RecordFilterStrategy<String, Object> {

    private final List<Provider> providers;
    private final KafkaPropertyConfig kafkaPropertyConfig;

    @Override
    public boolean filter(ConsumerRecord<String, Object> consumerRecord) {
        return false;
    }

    @Override
    public List<ConsumerRecord<String, Object>> filterBatch(List<ConsumerRecord<String, Object>> records) {
        if (kafkaPropertyConfig.isAsyncModeEnabled()) {
            List<ConsumerRecord<String, Object>> accept = records.stream()
                    .filter(record -> {
                        String providerHeader = new String(record.headers().lastHeader(KAFKA_PROVIDER_HEADER).value());
                        if (!providerHeader.isBlank()) {
                            return providers.stream().anyMatch(provider -> provider.getPropertyName().equalsIgnoreCase(providerHeader));
                        } else {
                            return true;
                        }
                    }).toList();
            return RecordFilterStrategy.super.filterBatch(accept);
        } else {
            return RecordFilterStrategy.super.filterBatch(records);
        }
    }
}
