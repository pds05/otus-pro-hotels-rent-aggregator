package ru.otus.java.pro.result.project.hotels.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@ToString(exclude = "hotelAmenities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel_amenity_groups")
public class HotelAmenityGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_amenity_groups_id_gen")
    @SequenceGenerator(name = "hotel_amenity_groups_id_gen", sequenceName = "hotel_amenity_groups_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotEmpty
    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "order_num")
    private Integer orderNum;

    @JsonIgnore
    @OneToMany(mappedBy = HotelAmenity_.HOTEL_AMENITY_GROUP)
    private Set<HotelAmenity> hotelAmenities = new LinkedHashSet<>();

}