package ru.otus.java.pro.result.project.messageprocessor.utils;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.java.pro.result.project.messageprocessor.entities.Provider;
import ru.otus.java.pro.result.project.messageprocessor.exceptions.ApplicationException;

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
