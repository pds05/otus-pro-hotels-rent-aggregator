package ru.otus.java.pro.result.project.hotels.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotels.daos.HotelDao;
import ru.otus.java.pro.result.project.hotels.dtos.HotelDtoRq;
import ru.otus.java.pro.result.project.hotels.entities.Hotel;
import ru.otus.java.pro.result.project.hotels.entities.HotelRoom;
import ru.otus.java.pro.result.project.hotels.entities.HotelRoomRate;
import ru.otus.java.pro.result.project.hotels.exceptions.ResourceNotFoundException;
import ru.otus.java.pro.result.project.hotels.repositories.HotelRoomsRepository;
import ru.otus.java.pro.result.project.hotels.repositories.HotelsRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelsServiceImpl implements HotelsService {
    private final HotelsRepository hotelsRepository;
    private final HotelRoomsRepository hotelRoomsRepository;
    private final HotelDao hotelDao;

    @Override
    public List<Hotel> getHotels(String city) {
        return hotelsRepository.findAllByCity_Title(city);
    }

    @Override
    public List<Hotel> getFilterHotels(HotelDtoRq hotelDtoRq) {
        List<Hotel> hotels = hotelDao.getFilterHotels(hotelDtoRq);
        return postQueryFilter(hotels, hotelDtoRq);
    }

    @Override
    public Hotel getHotel(int hotelId) {
        return hotelsRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel not exist"));
    }

    @Override
    public List<HotelRoom> getHotelRooms(int hotelId) {
        return hotelRoomsRepository.findByHotel_Id(hotelId);
    }

    @Override
    public HotelRoom getHotelRoom(int hotelRoomId, int hotelId) {
        return hotelRoomsRepository.findByIdAndHotel_Id(hotelRoomId, hotelId).orElseThrow(() -> new ResourceNotFoundException("Room not exist"));
    }

    @Override
    public List<HotelRoom> getHotelRoomsWithOrders(int hotelId) {
        return hotelRoomsRepository.findWithUserOrdersByHotel_Id(hotelId);
    }

    @Override
    public HotelRoom getHotelRoomWithOrders(int hotelRoomId, int hotelId) {
        return hotelRoomsRepository.findWithUserOrdersByIdAndHotel_Id(hotelRoomId, hotelId).orElseThrow(() -> new ResourceNotFoundException("Room not exist"));
    }

    private List<Hotel> postQueryFilter(List<Hotel> hotels, HotelDtoRq filter) {
        if (filter.getHotelRoomAmenities() != null) {
            hotels = hotels.stream().peek(hotel -> {
                Set<HotelRoom> filtered = hotel.getHotelRooms().stream().filter(room ->
                        filter.getHotelRoomAmenities().stream().allMatch(filterAmenity -> room.getHotelRoomAmenities().stream().anyMatch(roomAmenity -> roomAmenity.getTitle().equalsIgnoreCase(filterAmenity)))
                ).collect(Collectors.toSet());
                hotel.setHotelRooms(filtered);
            }).filter(hotel -> !hotel.getHotelRooms().isEmpty()).toList();
        }
        if (filter.getBeds() != null) {
            hotels = hotels.stream().peek(hotel -> {
                Set<HotelRoom> filtered = hotel.getHotelRooms().stream().filter(room ->
                        filter.getBeds().stream().allMatch(filterBed -> room.getHotelRoomBeds().stream().anyMatch(roomBed -> roomBed.getBedType().getTitle().equalsIgnoreCase(filterBed)))
                ).collect(Collectors.toSet());
                hotel.setHotelRooms(filtered);
            }).filter(hotel -> !hotel.getHotelRooms().isEmpty()).toList();
        }
        if (filter.getFoods() != null) {
            hotels = hotels.stream().peek(hotel -> {
                        Set<HotelRoom> filteredRoom = hotel.getHotelRooms().stream().peek(
                                hotelRoom -> {
                                    Set<HotelRoomRate> filteredRate = hotelRoom.getHotelRoomRates().stream().filter(rate ->
                                            filter.getFoods().stream().allMatch(filterFood -> rate.getFeedType().getTitle().equalsIgnoreCase(filterFood))
                                    ).collect(Collectors.toSet());
                                    hotelRoom.setHotelRoomRates(filteredRate);
                                }
                        ).filter(hotelRoom -> !hotelRoom.getHotelRoomRates().isEmpty()).collect(Collectors.toSet());
                        hotel.setHotelRooms(filteredRoom);
                    }
            ).filter(hotel -> !hotel.getHotelRooms().isEmpty()).toList();
        }
        if (filter.getPriceFrom() != null || filter.getPriceTo() != null) {
            hotels = hotels.stream().peek(hotel -> {
                Set<HotelRoom> filteredRoom = hotel.getHotelRooms().stream().peek(
                        hotelRoom -> {
                            Set<HotelRoomRate> filteredRate = hotelRoom.getHotelRoomRates().stream().filter(rate ->
                                    new BigDecimal(Optional.ofNullable(filter.getPriceFrom()).orElse(0)).compareTo(rate.getPrice()) <= 0 &&
                                            (filter.getPriceTo() == null || new BigDecimal(filter.getPriceTo()).compareTo(rate.getPrice()) >= 0)

                            ).collect(Collectors.toSet());
                            hotelRoom.setHotelRoomRates(filteredRate);
                        }
                ).filter(hotelRoom -> !hotelRoom.getHotelRoomRates().isEmpty()).collect(Collectors.toSet());
                hotel.setHotelRooms(filteredRoom);
            }).filter(hotel -> !hotel.getHotelRooms().isEmpty()).toList();
        }
        return hotels;
    }
}
