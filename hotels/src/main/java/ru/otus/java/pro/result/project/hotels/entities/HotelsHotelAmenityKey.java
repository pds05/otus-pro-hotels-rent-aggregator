package ru.otus.java.pro.result.project.hotels.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@Embeddable
public class HotelsHotelAmenityKey implements Serializable {
    @Column(name = "hotel_id")
    private Integer hotelId;

    @Column(name = "hotel_amenity_id")
    private Integer hotelAmenityId;
}
