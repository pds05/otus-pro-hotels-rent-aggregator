package ru.otus.java.pro.result.project.hotels.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class HotelRoomBedKey implements Serializable {
    @NotNull
    @Column(name = "hotel_room_id")
    private Integer hotelRoomId;

    @NotNull
    @Column(name = "bed_type_id")
    private Integer bedTypeId;
}
