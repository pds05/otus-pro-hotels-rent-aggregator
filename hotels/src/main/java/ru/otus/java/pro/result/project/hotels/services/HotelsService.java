package ru.otus.java.pro.result.project.hotels.services;

import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotels.entities.Hotel;

import java.util.List;
import java.util.Optional;

@Service
public interface HotelsService {

    List<Hotel> findHotels(String city);

    Optional<Hotel> findHotel(int hotelId, String city);
}
