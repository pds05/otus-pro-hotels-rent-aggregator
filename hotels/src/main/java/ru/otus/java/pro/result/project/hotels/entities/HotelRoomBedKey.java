package ru.otus.java.pro.result.project.hotels.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class HotelRoomBedKey implements Serializable {
    @Column(name = "hotel_room_id")
    private Integer hotelRoomId;

    @Column(name = "bed_type_id")
    private Integer bedTypeId;
}
