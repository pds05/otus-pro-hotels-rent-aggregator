package ru.otus.java.pro.result.project.hotels.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotels.entities.Hotel;
import ru.otus.java.pro.result.project.hotels.entities.HotelRoom;
import ru.otus.java.pro.result.project.hotels.exceptions.ResourceNotFoundException;
import ru.otus.java.pro.result.project.hotels.repositories.HotelRoomsRepository;
import ru.otus.java.pro.result.project.hotels.repositories.HotelsRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelsServiceImpl implements HotelsService {
    private final HotelsRepository hotelsRepository;
    private final HotelRoomsRepository hotelRoomsRepository;

    @Override
    public List<Hotel> findHotels(String city) {
        return hotelsRepository.findAllByCity_Title(city);
    }

    @Override
    public Optional<Hotel> findHotel(int hotelId, String city) {
        return hotelsRepository.findByIdAndCity_Title(hotelId, city);
    }

    @Override
    public Optional<Hotel> findHotel(int hotelId) {
        return hotelsRepository.findById(hotelId);
    }

    @Override
    public List<HotelRoom> findHotelRooms(int hotelId) {
        return hotelRoomsRepository.findByHotel_Id(hotelId);
    }

    @Override
    public Optional<HotelRoom> findHotelRoom(int hotelRoomId, int hotelId) {
        return hotelRoomsRepository.findByIdAndHotel_Id(hotelRoomId, hotelId);
    }

    @Override
    public List<HotelRoom> findHotelRoomsWithOrders(int hotelId) {
        return hotelRoomsRepository.findWithUserOrdersByHotel_Id(hotelId);
    }

    @Override
    public Optional<HotelRoom> findHotelRoomWithOrders(int hotelRoomId, int hotelId) {
        return hotelRoomsRepository.findWithUserOrdersByIdAndHotel_Id(hotelRoomId, hotelId);
    }

}
