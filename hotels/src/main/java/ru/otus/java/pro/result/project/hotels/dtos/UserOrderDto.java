package ru.otus.java.pro.result.project.hotels.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Основная информация о бронировании")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderDto {
    @Schema(description = "Статус бронирования")
    private String status;
    @Schema(description = "Номер бронирования", example = "00000001-1")
    private String order;
    @Schema(description = "Время бронирования", format = "date-time", type = "string", example = "2025-10-20 12:58:09")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateIn;
    @Schema(description = "Дата выезда", format = "date", type = "string", example = "2025-10-26")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOut;
    @Schema(description = "Количество ночей", type = "integer", example = "1")
    private Long nights;
    @Schema(description = "Дополнительная информация")
    private String description;
    @Schema(description = "Возможность возврата оплаты", type = "boolean")
    private Boolean refund;

}
