package ru.otus.java.pro.result.project.hotels.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ct_cities")
public class CtCity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ct_cities_id_gen")
    @SequenceGenerator(name = "ct_cities_id_gen", sequenceName = "ct_cities_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "phone_prefix", length = 6)
    private String phonePrefix;

    @NotEmpty
    @Column(name = "title", length = 50)
    private String title;

}