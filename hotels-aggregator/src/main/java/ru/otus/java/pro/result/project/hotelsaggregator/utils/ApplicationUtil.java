package ru.otus.java.pro.result.project.hotelsaggregator.utils;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.Provider;
import ru.otus.java.pro.result.project.hotelsaggregator.enums.BusinessMethodEnum;
import ru.otus.java.pro.result.project.hotelsaggregator.exceptions.ApplicationException;

import java.util.List;

@AllArgsConstructor
@Component
public class ApplicationUtil {

    private static List<Provider> providersList;

    @Autowired
    public void setProviders(List<Provider> providers) {
        providersList = providers;
    }

    public static Provider getProvider(String providerName) {
        return providersList.stream().filter(provider -> provider.getPropertyName().contains(providerName)).findFirst().orElseThrow(() -> new ApplicationException("Provider '" + providerName + "' not found"));
    }

}
