package ru.otus.java.pro.result.project.hotels.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel_amenities")
public class HotelAmenity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_amenities_id_gen")
    @SequenceGenerator(name = "hotel_amenities_id_gen", sequenceName = "hotel_amenities_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "hotel_amenity_group_id")
    private HotelAmenityGroup hotelAmenityGroup;

    @NotEmpty
    @Column(name = "title", nullable = false, length = 50)
    private String title;

}