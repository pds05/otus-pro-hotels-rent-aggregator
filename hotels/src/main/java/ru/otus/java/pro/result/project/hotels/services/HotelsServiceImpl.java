package ru.otus.java.pro.result.project.hotels.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotels.daos.HotelDao;
import ru.otus.java.pro.result.project.hotels.dtos.HotelDtoRq;
import ru.otus.java.pro.result.project.hotels.entities.Hotel;
import ru.otus.java.pro.result.project.hotels.entities.HotelRoom;
import ru.otus.java.pro.result.project.hotels.exceptions.ResourceNotFoundException;
import ru.otus.java.pro.result.project.hotels.repositories.HotelRoomsRepository;
import ru.otus.java.pro.result.project.hotels.repositories.HotelsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelsServiceImpl implements HotelsService {
    private final HotelsRepository hotelsRepository;
    private final HotelRoomsRepository hotelRoomsRepository;
    private final HotelDao hotelDao;

    @Override
    public List<Hotel> findHotels(String city) {
        return hotelsRepository.findAllByCity_Title(city);
    }

    @Override
    public List<Hotel> findFilterHotels(HotelDtoRq hotelDtoRq) {
        return hotelDao.getFilterHotels(hotelDtoRq);
    }

    @Override
    public Hotel findHotel(int hotelId) {
        return hotelsRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel not exist"));
    }

    @Override
    public List<HotelRoom> findHotelRooms(int hotelId) {
        return hotelRoomsRepository.findByHotel_Id(hotelId);
    }

    @Override
    public HotelRoom findHotelRoom(int hotelRoomId, int hotelId) {
        return hotelRoomsRepository.findByIdAndHotel_Id(hotelRoomId, hotelId).orElseThrow(() -> new ResourceNotFoundException("Room not exist"));
    }

    @Override
    public List<HotelRoom> findHotelRoomsWithOrders(int hotelId) {
        return hotelRoomsRepository.findWithUserOrdersByHotel_Id(hotelId);
    }

    @Override
    public HotelRoom findHotelRoomWithOrders(int hotelRoomId, int hotelId) {
        return hotelRoomsRepository.findWithUserOrdersByIdAndHotel_Id(hotelRoomId, hotelId).orElseThrow(() -> new ResourceNotFoundException("Room not exist"));
    }

}
