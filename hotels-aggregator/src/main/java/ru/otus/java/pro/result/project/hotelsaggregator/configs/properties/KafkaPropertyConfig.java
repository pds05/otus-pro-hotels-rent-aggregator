package ru.otus.java.pro.result.project.hotelsaggregator.configs.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Setter
@Getter
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaPropertyConfig {

    private List<String> brokers;

    private String consumerGroup;

    private Long backoffMaxFailure;

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration backoffInterval;

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration sendTimeout;

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration commonReplyTimeout;

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration replyTimeout;

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration replyContainerInitTimeout;

    private KafkaTopicsProperty topics;

    private AsyncMode asyncMode;

    public boolean isAsyncModeEnabled() {
        if (asyncMode != null) {
            return asyncMode.isEnable();
        } else return false;
    }

    @Getter
    @Setter
    public static class AsyncMode {

        private boolean enable = false; //default Producer sent message in single topic per business method

        private boolean partialOnTimeout = false; //default value

        private String topicPrefix;

        @DurationUnit(ChronoUnit.SECONDS)
        private Duration commonReplyTimeout;

        @DurationUnit(ChronoUnit.SECONDS)
        private Duration replyTimeout;
    }

    @Getter
    @Setter
    public static class KafkaTopicsProperty {

        private Integer partitionsCount;

        private Integer replicasCount;

        private String requestTopicSuffix;

        public void setRequestTopicSuffix(String requestTopicSuffix) {
            this.requestTopicSuffix = requestTopicSuffix.toLowerCase();
        }

        private String replyTopicSuffix;

        public void setReplyTopicSuffix(String replyTopicSuffix) {
            this.replyTopicSuffix = replyTopicSuffix.toLowerCase();
        }
    }
}
