package ru.otus.java.pro.result.project.hotels.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.java.pro.result.project.hotels.entities.UserOrder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Function;

import static java.time.temporal.ChronoUnit.DAYS;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserOrderDto {
    private String status;
    private String order;
    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime dateOrdered;
    private BigDecimal price;
    private String clientName;
    private String hotel;
    private String address;
    private String room;
    private String rate;
    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate dateIn;
    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate dateOut;
    private Long nights;
    private String description;

    private static final Function<UserOrder, UserOrderDto> USER_ORDER_ENTITY_TO_DTO = order -> new UserOrderDto(order.getStatus().name().toLowerCase(), order.getUserOrderId().print(), order.getCreatedAt(), order.getOrderPrice(), order.getUserProfile().printFullName(), order.getHotelRoom().getHotel().getTitle(), order.getHotelRoom().getHotel().printAddress(), order.getHotelRoom().getTitle(), order.getHotelRoomRate().getTitle(), order.getDateIn(), order.getDateOut(), DAYS.between(order.getDateIn(), order.getDateOut()), order.getDescription());

    public static UserOrderDto mapping(UserOrder order) {
        return USER_ORDER_ENTITY_TO_DTO.apply(order);
    }

}
