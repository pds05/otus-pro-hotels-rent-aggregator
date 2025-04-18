package ru.otus.java.pro.result.project.hotels.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@ToString(exclude = {"hotelRoom", "primaryKey"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel_room_beds")
public class HotelRoomBed {
    @EmbeddedId
    private HotelRoomBedKey primaryKey;

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "hotel_room_id", nullable = false)
    @MapsId(HotelRoomBedKey_.HOTEL_ROOM_ID)
    private HotelRoom hotelRoom;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "bed_type_id", nullable = false)
    @MapsId(HotelRoomBedKey_.BED_TYPE_ID)
    private CtHotelBedType bedType;

    @Positive
    @Column(name = "amount")
    private Short amount;

}