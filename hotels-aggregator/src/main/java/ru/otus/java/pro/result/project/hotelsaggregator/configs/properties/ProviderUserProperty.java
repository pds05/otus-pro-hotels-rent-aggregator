package ru.otus.java.pro.result.project.hotelsaggregator.configs.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "common.provider-user-profile")
public class ProviderUserProperty {

    private String emailDomain;

    private boolean emailByRandom;

    private boolean emailByPhoneNumber;

    private Integer emailUserLength = 8; //default value

}
