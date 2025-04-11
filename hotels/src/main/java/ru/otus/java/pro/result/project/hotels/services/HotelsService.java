package ru.otus.java.pro.result.project.hotels.services;

import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotels.dtos.HotelDtoRq;
import ru.otus.java.pro.result.project.hotels.entities.Hotel;
import ru.otus.java.pro.result.project.hotels.entities.HotelRoom;

import java.util.List;

@Service
public interface HotelsService {

    List<Hotel> getHotels(String city);

    List<Hotel> getFilterHotels(HotelDtoRq hotelDtoRq);

    Hotel getHotel(int hotelId);

    List<HotelRoom> getHotelRooms(int hotelId);

    HotelRoom getHotelRoom(int hotelRoomId, int hotelId);

    List<HotelRoom> getHotelRoomsWithOrders(int hotelId);

    HotelRoom getHotelRoomWithOrders(int hotelRoomId, int hotelId);
}
