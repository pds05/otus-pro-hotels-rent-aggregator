package ru.otus.java.pro.result.project.hotels.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel_room_rate")
public class HotelRoomRate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_room_rate_id_gen")
    @SequenceGenerator(name = "hotel_room_rate_id_gen", sequenceName = "hotel_room_rate_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "hotel_rooms_id")
    private HotelRoom hotelRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "feed_type_id")
    private CtHotelFeedType feedType;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "payment_type", length = 50)
    private String paymentType;

    @Column(name = "is_refund")
    private Boolean isRefund;

}