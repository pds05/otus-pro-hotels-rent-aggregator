package ru.otus.java.pro.result.project.hotels.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionService;
import ru.otus.java.pro.result.project.hotels.dtos.HotelTypeDtoRqConverter;

@Configuration
public class WebMvcConfigurationSupport extends org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport {
    @Override
    public FormattingConversionService mvcConversionService() {
        FormattingConversionService f = super.mvcConversionService();
        f.addConverter(new HotelTypeDtoRqConverter());
        return f;
    }
}
