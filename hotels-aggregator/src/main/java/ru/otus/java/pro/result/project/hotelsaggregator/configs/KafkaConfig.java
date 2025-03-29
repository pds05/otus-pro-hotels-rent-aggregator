package ru.otus.java.pro.result.project.hotelsaggregator.configs;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.HotelDto;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.HotelDtoRq;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.Provider;

import java.util.List;

@AllArgsConstructor
@Configuration
public class KafkaConfig {
    private final ApplicationConfig applicationConfig;
    private final List<Provider> providers;

    @Bean
    public ReplyingKafkaTemplate<String, HotelDtoRq, List<HotelDto>> replyingTemplate(
            ProducerFactory<String, HotelDtoRq> pf,
            ConcurrentMessageListenerContainer<String, List<HotelDto>> repliesContainer) {

        return new ReplyingKafkaTemplate<>(pf, repliesContainer);
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, List<HotelDto>> repliesContainer(
            ConcurrentKafkaListenerContainerFactory<String, List<HotelDto>> containerFactory) {

        List<String> listReplyTopics = providers.stream().map(provider -> provider.getPropertyName().concat(applicationConfig.getReplyTopicSuffix())).toList();
        String[] arrReplyTopics = new String[listReplyTopics.size()];
        arrReplyTopics = listReplyTopics.toArray(arrReplyTopics);
        ConcurrentMessageListenerContainer<String, List<HotelDto>> repliesContainer =
                containerFactory.createContainer(arrReplyTopics);
        repliesContainer.getContainerProperties().setGroupId(applicationConfig.getReplyTopicSuffix() + "Group");
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }

    @Bean
    public KafkaAdmin.NewTopics requestTopics() {
        List<NewTopic> topicList = providers.stream().map(provider -> TopicBuilder.name(provider.getPropertyName().concat(applicationConfig.getRequestTopicSuffix()))
                .partitions(2)
                .replicas(3)
                .build()).toList();
        NewTopic[] topicArray = new NewTopic[topicList.size()];
        topicArray = topicList.toArray(topicArray);
        return new KafkaAdmin.NewTopics(topicArray);
    }

    @Bean
    public KafkaAdmin.NewTopics replyTopics() {
        List<NewTopic> topicList = providers.stream().map(provider -> TopicBuilder.name(provider.getPropertyName().concat(applicationConfig.getReplyTopicSuffix()))
                .partitions(2)
                .replicas(3)
                .build()).toList();
        NewTopic[] topicArray = new NewTopic[topicList.size()];
        topicArray = topicList.toArray(topicArray);
        return new KafkaAdmin.NewTopics(topicArray);
    }

}
