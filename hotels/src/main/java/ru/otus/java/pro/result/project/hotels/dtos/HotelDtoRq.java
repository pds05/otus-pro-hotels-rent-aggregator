package ru.otus.java.pro.result.project.hotels.dtos;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDtoRq {
    private String city;
    private String hotel;
    private Integer stars;
    private Integer priceFrom;
    private Integer priceTo;
    private List<HotelTypeDtoRq> hotelTypes;
    private  HotelTypeDtoRq hotelType;


    public enum HotelTypeDtoRq {
        HOTEL("Отель"), HOSTEL("Хостел"), APARTMENTS("Апартаменты, квартира"), GUEST_HOUSE("Гостевой дом"), COTTAGE("Коттедж, вилла, бунгало"), SANATORIUM("Санаторий"), CAMPING("Кемпинг");

        private final String title;

        HotelTypeDtoRq(String title) {
            this.title = title;
        }

        public String getDescription() {
            return title;
        }
        //FIXME Not working string to enum insensitive mapping with @JsonCreator
        //FIXME Workaround solution to use WebMvcConfigurationSupport with custom Converter
        @JsonCreator
        public static HotelTypeDtoRq fromString(String value) {
            return EnumUtils.findEnumInsensitiveCase(HotelTypeDtoRq.class, value);
        }
    }
}
