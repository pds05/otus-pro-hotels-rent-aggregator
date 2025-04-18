package ru.otus.java.pro.result.project.hotels.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.*;
import ru.otus.java.pro.result.project.hotels.enums.UserOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_orders")
public class UserOrder {
    @EmbeddedId
    private UserOrderKey userOrderId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_room_id", referencedColumnName = "id")
    private HotelRoom hotelRoom;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_room_rate_id", referencedColumnName = "id")
    private HotelRoomRate hotelRoomRate;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_profile_id", insertable = false, updatable = false)
    private UserProfile userProfile;

    @Column(name = "date_in")
    private LocalDate dateIn;

    @Column(name = "date_out")
    private LocalDate dateOut;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserOrderStatus status;

    @PositiveOrZero
    @Column(name = "order_price", precision = 10, scale = 2)
    private BigDecimal orderPrice;

    @Column(name = "is_refund")
    private Boolean isRefund;

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = OrderGuest_.USER_ORDER)
    private Set<OrderGuest> orderGuests = new LinkedHashSet<>();

}