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
    private final CtHotelTypeRepository ctHotelTypeRepository;
    private final CtHotelAmenitiesRepository ctHotelAmenitiesRepository;
    private final CtHotelRoomAmenitiesRepository ctHotelRoomAmenitiesRepository;
    private final CtHotelFeedTypeRepository ctHotelFeedTypeRepository;
    private final CtHotelBedTypeRepository ctHotelBedTypeRepository;

    private final List<CtHotelType> hotelTypes;
    private final List<HotelAmenity> hotelAmenities;
    private final List<HotelRoomAmenity> hotelRoomAmenities;
    private final List<CtHotelFeedType> ctHotelFeedTypes;
    private final List<CtHotelBedType> ctHotelBedTypes;

    public RequestParametersCache(CtHotelTypeRepository ctHotelTypeRepository, CtHotelAmenitiesRepository ctHotelAmenitiesRepository, CtHotelRoomAmenitiesRepository ctHotelRoomAmenitiesRepository, CtHotelFeedTypeRepository ctHotelFeedTypeRepository, CtHotelBedTypeRepository ctHotelBedTypeRepository) {
        this.ctHotelTypeRepository = ctHotelTypeRepository;
        this.hotelTypes = ctHotelTypeRepository.findAll();

        this.ctHotelAmenitiesRepository = ctHotelAmenitiesRepository;
        this.hotelAmenities = ctHotelAmenitiesRepository.findAll();

        this.ctHotelRoomAmenitiesRepository = ctHotelRoomAmenitiesRepository;
        this.hotelRoomAmenities = ctHotelRoomAmenitiesRepository.findAll();

        this.ctHotelFeedTypeRepository = ctHotelFeedTypeRepository;
        this.ctHotelFeedTypes = ctHotelFeedTypeRepository.findAll();

        this.ctHotelBedTypeRepository = ctHotelBedTypeRepository;
        this.ctHotelBedTypes = ctHotelBedTypeRepository.findAll();
    }
}
