package ru.otus.java.pro.result.project.hotels.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotels.entities.Hotel;
import ru.otus.java.pro.result.project.hotels.repositories.HotelsRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelsServiceImpl implements HotelsService {
    private final HotelsRepository hotelsRepository;

    @Override
    public List<Hotel> findHotels(String city) {
        return hotelsRepository.findAllByCity_Title(city);
    }

    @Override
    public Optional<Hotel> findHotel(int hotelId, String city) {
        return hotelsRepository.findByIdAndCity_Title(hotelId, city);
    }
}
