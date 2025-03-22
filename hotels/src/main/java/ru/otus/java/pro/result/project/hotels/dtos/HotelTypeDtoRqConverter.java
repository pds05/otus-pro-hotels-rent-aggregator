package ru.otus.java.pro.result.project.hotels.dtos;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.EnumUtils;

@Component
public class HotelTypeDtoRqConverter implements Converter<String, HotelDtoRq.HotelTypeDtoRq> {

    @Override
    public HotelDtoRq.HotelTypeDtoRq convert(String source) {
        return EnumUtils.findEnumInsensitiveCase(HotelDtoRq.HotelTypeDtoRq.class, source);
    }
}
