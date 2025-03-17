package ru.otus.java.pro.result.project.hotels.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "hotel_amenities")
public class HotelAmenity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_amenities_id_gen")
    @SequenceGenerator(name = "hotel_amenities_id_gen", sequenceName = "hotel_amenities_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "hotel_amenity_group_id")
    private HotelAmenityGroup hotelAmenityGroup;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

}