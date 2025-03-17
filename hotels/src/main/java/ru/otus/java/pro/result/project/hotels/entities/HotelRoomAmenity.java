package ru.otus.java.pro.result.project.hotels.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel_room_amenities")
public class HotelRoomAmenity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_room_amenities_id_gen")
    @SequenceGenerator(name = "hotel_room_amenities_id_gen", sequenceName = "hotel_room_amenities_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

}