package ru.otus.java.pro.result.project.hotels.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ct_hotel_bed_types")
public class CtHotelBedType {
    @Id
    @SequenceGenerator(name = "ct_hotel_bed_types_id_gen", sequenceName = "ct_hotel_bed_types_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Short id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "description")
    private String description;

}