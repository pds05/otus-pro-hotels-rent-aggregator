package ru.otus.java.pro.result.project.hotels.services;

import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotels.entities.Hotel;
import ru.otus.java.pro.result.project.hotels.entities.HotelRoom;

import java.util.List;
import java.util.Optional;

@Service
public interface HotelsService {

    List<Hotel> findHotels(String city);

    Optional<Hotel> findHotel(int hotelId, String city);

    Optional<Hotel> findHotel(int hotelId);

    List<HotelRoom> findHotelRooms(int hotelId);

    Optional<HotelRoom> findHotelRoom(int hotelRoomId, int hotelId);

    List<HotelRoom> findHotelRoomsWithOrders(int hotelId);

    Optional<HotelRoom> findHotelRoomWithOrders(int hotelRoomId, int hotelId);
}
