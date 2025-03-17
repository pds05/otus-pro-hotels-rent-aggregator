package ru.otus.java.pro.result.project.hotels.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotels_hotel_amenities")
public class HotelsHotelAmenity {
    @EmbeddedId
    private HotelsHotelAmenityKey primaryKey;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "hotel_id", nullable = false)
    @MapsId(HotelsHotelAmenityKey_.HOTEL_ID)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "hotel_amenity_id")
    @MapsId(HotelsHotelAmenityKey_.HOTEL_AMENITY_ID)
    private HotelAmenity hotelAmenity;

    @ColumnDefault("false")
    @Column(name = "is_special")
    private Boolean isSpecial;

    @ColumnDefault("false")
    @Column(name = "is_additional_cost")
    private Boolean isAdditionalCost;

    @Column(name = "description")
    private String description;

}