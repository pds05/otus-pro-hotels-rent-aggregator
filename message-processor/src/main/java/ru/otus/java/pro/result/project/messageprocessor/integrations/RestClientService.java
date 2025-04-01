package ru.otus.java.pro.result.project.messageprocessor.integrations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import ru.otus.java.pro.result.project.messageprocessor.configs.RestClientRegistrator;
import ru.otus.java.pro.result.project.messageprocessor.dtos.HotelDto;
import ru.otus.java.pro.result.project.messageprocessor.dtos.HotelDtoRq;
import ru.otus.java.pro.result.project.messageprocessor.entities.ProviderApi;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.*;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@Service
public class RestClientService {
    private final ApplicationContext context;
    private final ObjectMapper objectMapper;

    public <T> List<T> getRequest(ProviderApi api, Object request, Class<T> clazz) {
        UriBuilder uriBuilder = UriComponentsBuilder.fromPath(api.getPath());
//        objectMapper.convertValue(request, LinkedMultiValueMap.class).forEach(uriBuilder::queryParam); //.forEach(uriBuilder::queryParam);

        Map<String, String> map = objectMapper.convertValue(request, new TypeReference<Map<String,String>>() {});
        LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
        map.forEach(linkedMultiValueMap::add);
        URI uri = uriBuilder.queryParams(linkedMultiValueMap).build();
        RestClient restClient = getRestClient(api.getProvider().getPropertyName());
        return restClient.get()
                .uri(uri)
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<ArrayList<T>>() {});
    }

    public <T> T postRequest(ProviderApi api, Class<T> dtoClass) {
        return getRestClient(api.getProvider().getPropertyName()).post().uri(api.getPath())
                .body(dtoClass)
                .retrieve()
                .body(dtoClass);
    }

    private RestClient getRestClient(String providerName) {
        String beanName = providerName + RestClientRegistrator.REST_CLIENT_BEAN_SUFFIX;
        return context.getBean(beanName, RestClient.class);
    }
}
