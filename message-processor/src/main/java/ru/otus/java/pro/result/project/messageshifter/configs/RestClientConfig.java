package ru.otus.java.pro.result.project.messageshifter.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
@Configuration
public class RestClientConfig implements BeanDefinitionRegistryPostProcessor {
    private final ApplicationConfig applicationConfig;
    private final ApplicationContext applicationContext;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        applicationConfig.getIntegrationServices().entrySet().stream().forEach(entry -> {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ClientHttpRequestFactory.class);
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(entry.getValue().getConnectTimeout());
            factory.setReadTimeout(entry.getValue().getReadTimeout());
            String factoryBeanName = applicationConfig.getApiClientBeanPrefix() + applicationConfig.firstCharacterToUpperCase(entry.getKey()) + "Factory";
            registry.registerBeanDefinition(factoryBeanName, builder.getBeanDefinition());

            builder = BeanDefinitionBuilder.genericBeanDefinition(RestClient.class);
            RestClient.builder()
                    .requestFactory((ClientHttpRequestFactory) applicationContext.getBean(factoryBeanName))
                    .baseUrl(entry.getValue().getUrl())
                    .build();
            registry.registerBeanDefinition(applicationConfig.getApiClientBeanPrefix() + applicationConfig.firstCharacterToUpperCase(entry.getKey()) + "RestClient", builder.getBeanDefinition());
        });
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinitionRegistryPostProcessor.super.postProcessBeanFactory(beanFactory);

    }

    public RestClient getRestClient(String serviceName) {
        return applicationContext.getBean(serviceName, RestClient.class);
    }
}
