package ru.otus.java.pro.result.project.hotels.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_orders")
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_orders_id_gen")
    @SequenceGenerator(name = "user_orders_id_gen", sequenceName = "user_orders_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ColumnDefault("now()")
    @Column(name = "order_date")
    private Instant orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    @Column(name = "date_from")
    private LocalDate dateFrom;

    @Column(name = "date_to")
    private LocalDate dateTo;

    @ColumnDefault("false")
    @Column(name = "is_complete")
    private Boolean isComplete;

    @ColumnDefault("false")
    @Column(name = "is_canceled")
    private Boolean isCanceled;

    @Column(name = "order_price", precision = 10, scale = 2)
    private BigDecimal orderPrice;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "userOrder")
    private Set<OrderGuest> orderGuests = new LinkedHashSet<>();

    @ManyToMany
    private Set<HotelRoom> hotelRooms = new LinkedHashSet<>();

}