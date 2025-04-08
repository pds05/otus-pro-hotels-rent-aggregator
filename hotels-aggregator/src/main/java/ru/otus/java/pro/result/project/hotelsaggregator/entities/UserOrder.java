package ru.otus.java.pro.result.project.hotelsaggregator.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import ru.otus.java.pro.result.project.hotelsaggregator.enums.UserOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_orders")
public class UserOrder {
    @EmbeddedId
    private UserOrderKey userOrderId;

    @NotNull
    @MapsId("userProfileId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provider_user_profile_id", nullable = false)
    private ProviderUserProfile providerUserProfile;

    @Column(name = "provider_order", length = 50)
    private String providerOrderId;

    @Column(name = "hotel")
    private String hotel;

    @Column(name = "location")
    private String location;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "rate_name")
    private String rateName;

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

}