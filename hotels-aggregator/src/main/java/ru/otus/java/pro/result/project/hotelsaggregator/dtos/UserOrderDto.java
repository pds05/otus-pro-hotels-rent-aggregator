package ru.otus.java.pro.result.project.hotelsaggregator.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.UserOrder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Function;

import static java.time.temporal.ChronoUnit.DAYS;

@Schema(description = "Детальная информация о бронировании")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserOrderDto {
    @Schema(description = "Статус бронирования")
    private String status;
    @Schema(description = "Номер бронирования в сервисе", example = "00000001-1")
    private String externalOrder;
    @Schema(description = "Внутренний номер бронирования", example = "0000000001-1")
    private String internalOrder;
    @Schema(description = "Время бронирования", format = "date-time", type = "string", example = "2025-10-20 12:58:09")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOrdered;
    @Schema(description = "Стоимость бронирования", type = "number", example = "8500.00")
    private BigDecimal price;
    @Schema(description = "ФИО пользователя")
    private String clientName;
    @Schema(description = "Название отеля")
    private String hotel;
    @Schema(description = "Адрес отеля")
    private String address;
    @Schema(description = "Название номера")
    private String room;
    @Schema(description = "Название тарифа")
    private String rate;
    @Schema(description = "Дата заезда", format = "date", type = "string", example = "2025-10-25")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateIn;
    @Schema(description = "Дата выезда", format = "date", type = "string", example = "2025-10-26")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOut;
    @Schema(description = "Количество ночей", type = "integer", example = "1")
    private Long nights;
    @Schema(description = "Дополнительная информация")
    private String description;

    private static final Function<UserOrder, UserOrderDto> USER_ORDER_ENTITY_TO_DTO = order -> new UserOrderDto(order.getStatus().toLowerCase(), order.getProviderOrderId(), order.getPrimaryKey().print(), order.getCreatedAt(), order.getOrderPrice(), order.getUserProfile().printFullName(), order.getHotel(), order.getLocation(), order.getRoomName(), order.getRateName(), order.getDateIn(), order.getDateOut(), DAYS.between(order.getDateIn(), order.getDateOut()), order.getDescription());

    public static UserOrderDto mapping(UserOrder order) {
        return USER_ORDER_ENTITY_TO_DTO.apply(order);
    }

}
