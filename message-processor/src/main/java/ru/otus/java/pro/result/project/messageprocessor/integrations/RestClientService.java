package ru.otus.java.pro.result.project.messageprocessor.integrations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import ru.otus.java.pro.result.project.messageprocessor.configs.RestClientRegistrator;
import ru.otus.java.pro.result.project.messageprocessor.dtos.providers.AbstractProviderDto;
import ru.otus.java.pro.result.project.messageprocessor.entities.ProviderApi;
import ru.otus.java.pro.result.project.messageprocessor.enums.RestMethodEnum;
import ru.otus.java.pro.result.project.messageprocessor.exceptions.ProviderException;

import java.net.URI;
import java.util.Map;

import static org.springframework.http.MediaType.*;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@Service(value = "restService")
public class RestClientService implements RestService {
    private final ApplicationContext context;
    private final ObjectMapper objectMapper;

    public <T> T get(ProviderApi api, AbstractProviderDto request, Class<T> responseClass) {
        RestClient.ResponseSpec responseSpec = doGetRequest(api, request);
        T response = responseSpec.body(responseClass);
        logRestResponse(api, response, null);
        return response;
    }

    @Override
    public <T> T getAsList(ProviderApi api, AbstractProviderDto request) {
        RestClient.ResponseSpec responseSpec = doGetRequest(api, request);
        T response = responseSpec.body(new ParameterizedTypeReference<T>() {
        });
        logRestResponse(api, response, null);
        return response;
    }

    private RestClient.ResponseSpec doGetRequest(ProviderApi api, AbstractProviderDto request) {
        if (RestMethodEnum.valueOf(api.getRestMethod()) != RestMethodEnum.GET) {
            throw new ProviderException("REST_METHOD_ERROR", "Rest method " + api.getRestMethod() + " is not supported");
        }
        UriBuilder uriBuilder = UriComponentsBuilder.fromPath(api.getPath());
        Map<String, String> map = objectMapper.convertValue(request, new TypeReference<>() {
        });
        LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
        map.forEach(linkedMultiValueMap::add);
        URI uri = uriBuilder.queryParams(linkedMultiValueMap).build();
        RestClient restClient = getRestClient(api.getProvider().getPropertyName());
        logRestRequest(api, request, uri);
        return restClient.get().uri(uri)
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, resp) -> {
                    log.warn("Failed request: {}", req);
                    throw new ProviderException("REQUEST_ERROR", "Failed request, status=" + resp.getStatusCode() + ", message=" + resp.getStatusText());
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, resp) -> {
                    log.warn("Failed request: {}", req);
                    throw new ProviderException("PROVIDER_ERROR", "Failed request, status=" + resp.getStatusCode() + ", message=" + resp.getStatusText());
                });
    }

    private void logRestResponse(ProviderApi api, Object response, URI uri) {
        if (log.isDebugEnabled()) {
            log.debug("Received response from provider={}, method={}, url={}, uri={}",
                    api.getProvider().getPropertyName(), api.getRestMethod(), api.getProvider().getApiUrl() + api.getPath(), uri);
        }
        if (log.isTraceEnabled()) {
            log.trace("Received response from provider={}, method={}, url={}, uri={}, body={}",
                    api.getProvider().getPropertyName(), api.getRestMethod(), api.getProvider().getApiUrl() + api.getPath(), uri, response);
        }
    }

    private void logRestRequest(ProviderApi api, AbstractProviderDto request, URI uri) {
        if (log.isDebugEnabled()) {
            log.debug("Sending request to provider={}, method={}, url={}, uri={}",
                    api.getProvider().getPropertyName(), api.getBusinessMethod(), api.getProvider().getApiUrl() + api.getPath(), uri);
        }
        if (log.isTraceEnabled() && api.getRestMethod().equalsIgnoreCase(RestMethodEnum.POST.name())) {
            log.trace("Sending request to provider={}, method={}, url={}, uri={}, body={}",
                    api.getProvider().getPropertyName(), api.getBusinessMethod(), api.getProvider().getApiUrl() + api.getPath(), uri, request);
        }
    }

    public <T> T post(ProviderApi api, AbstractProviderDto request, Class<T> responseClass) {
        if (RestMethodEnum.valueOf(api.getRestMethod()) != RestMethodEnum.POST) {
            throw new ProviderException("REST_METHOD_ERROR", "Rest method " + api.getRestMethod() + " is not supported");
        }
        UriBuilder uriBuilder = UriComponentsBuilder.fromPath(api.getPath());
        URI uri = uriBuilder.build();
        logRestRequest(api, request, uri);
        RestClient restClient = getRestClient(api.getProvider().getPropertyName());
        T response = restClient.post().uri(uri)
                .accept(APPLICATION_JSON)
                .body(request)
                .retrieve().onStatus(HttpStatusCode::is4xxClientError, (req, resp) -> {
                    log.warn("Failed request: {}", req);
                    throw new ProviderException("REQUEST_ERROR", "Failed request, status=" + resp.getStatusCode() + ", message=" + resp.getStatusText());
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, resp) -> {
                    log.warn("Failed request: {}", req);
                    throw new ProviderException("PROVIDER_ERROR", "Failed request, status=" + resp.getStatusCode() + ", message=" + resp.getStatusText());
                })
                .body(responseClass);
        logRestResponse(api, response, uri);
        return response;
    }

    private RestClient getRestClient(String providerName) {
        String beanName = providerName + RestClientRegistrator.REST_CLIENT_BEAN_SUFFIX;
        return context.getBean(beanName, RestClient.class);
    }
}
