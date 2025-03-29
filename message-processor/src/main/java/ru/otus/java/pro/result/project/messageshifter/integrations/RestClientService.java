package ru.otus.java.pro.result.project.messageshifter.integrations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.otus.java.pro.result.project.messageshifter.configs.RestClientConfig;

@Getter
@Setter
@AllArgsConstructor
public class RestClientService {
    private String serviceName;
    private RestClientConfig config;


    public <T> T getRequest(String uri, Class<T> dtoClass) {
        return config.getRestClient(serviceName).get().uri(uri).retrieve().body(dtoClass);
    }

    public <T> T postRequest(String uri, Class<T> dtoClass) {
        return config.getRestClient(serviceName).post().uri(uri).body(dtoClass).retrieve().body(dtoClass);
    }
}
