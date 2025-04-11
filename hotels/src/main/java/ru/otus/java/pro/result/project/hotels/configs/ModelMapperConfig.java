package ru.otus.java.pro.result.project.hotels.configs;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.java.pro.result.project.hotels.dtos.UserDto;
import ru.otus.java.pro.result.project.hotels.dtos.UserOrderDto;
import ru.otus.java.pro.result.project.hotels.entities.UserOrder;
import ru.otus.java.pro.result.project.hotels.entities.UserProfile;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        mapUserProfileToDto(mapper);
        mapUserOrderToDto(mapper);
        return mapper;
    }

    private void mapUserProfileToDto(ModelMapper mapper) {
        mapper.createTypeMap(UserProfile.class, UserDto.class)
                .addMapping(UserProfile::getId, UserDto::setId)
                .addMapping(UserProfile::getLogin, UserDto::setLogin)
                .addMapping(UserProfile::getFirstName, UserDto::setFirstName)
                .addMapping(UserProfile::getLastName, UserDto::setLastName)
                .addMapping(UserProfile::getEmail, UserDto::setEmail)
                .addMapping(UserProfile::getPhoneNumber, UserDto::setPhoneNumber);
    }

    private void mapUserOrderToDto(ModelMapper mapper) {
        Converter<UserOrder, Long> dataRangeConverter = uo -> DAYS.between(uo.getSource().getDateIn(), uo.getSource().getDateOut());
        mapper.createTypeMap(UserOrder.class, UserOrderDto.class)
                .addMapping(UserOrder::getStatus, UserOrderDto::setStatus)
                .addMapping(UserOrder::getUserOrderId, UserOrderDto::setOrder)
                .addMapping(UserOrder::getCreatedAt, UserOrderDto::setDateOrdered)
                .addMapping(UserOrder::getOrderPrice, UserOrderDto::setPrice)
                .addMapping(uo -> uo.getUserProfile().printFullName(), UserOrderDto::setClientName)
                .addMapping(uo -> uo.getHotelRoom().getHotel().getTitle(), UserOrderDto::setHotel)
                .addMapping(uo -> uo.getHotelRoom().getHotel().getLocation(), UserOrderDto::setAddress)
                .addMapping(uo -> uo.getHotelRoom().getTitle(), UserOrderDto::setRoom)
                .addMapping(uo -> uo.getHotelRoomRate().getTitle(), UserOrderDto::setRate)
                .addMapping(UserOrder::getDateIn, UserOrderDto::setDateIn)
                .addMapping(UserOrder::getDateOut, UserOrderDto::setDateOut)
                .addMapping(UserOrder::getDescription, UserOrderDto::setDescription)
                .addMapping(UserOrder::getIsRefund, UserOrderDto::setRefund)
                .addMappings(m -> m.using(dataRangeConverter).map(uo -> uo, UserOrderDto::setNights));
    }

}
