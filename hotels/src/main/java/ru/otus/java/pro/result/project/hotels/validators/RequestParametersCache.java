package ru.otus.java.pro.result.project.hotels.validators;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotels.entities.*;
import ru.otus.java.pro.result.project.hotels.repositories.*;

import java.util.List;

@Getter
@Component
@Service
public class RequestParametersCache {

    private final List<CtHotelType> hotelTypes;
    private final List<HotelAmenity> hotelAmenities;
    private final List<HotelRoomAmenity> hotelRoomAmenities;
    private final List<CtHotelFeedType> ctHotelFeedTypes;
    private final List<CtHotelBedType> ctHotelBedTypes;

    public RequestParametersCache(CtHotelTypeRepository ctHotelTypeRepository, CtHotelAmenitiesRepository ctHotelAmenitiesRepository, CtHotelRoomAmenitiesRepository ctHotelRoomAmenitiesRepository, CtHotelFeedTypeRepository ctHotelFeedTypeRepository, CtHotelBedTypeRepository ctHotelBedTypeRepository) {

        this.hotelTypes = ctHotelTypeRepository.findAll();

        this.hotelAmenities = ctHotelAmenitiesRepository.findAll();

        this.hotelRoomAmenities = ctHotelRoomAmenitiesRepository.findAll();

        this.ctHotelFeedTypes = ctHotelFeedTypeRepository.findAll();

        this.ctHotelBedTypes = ctHotelBedTypeRepository.findAll();

    }
}
