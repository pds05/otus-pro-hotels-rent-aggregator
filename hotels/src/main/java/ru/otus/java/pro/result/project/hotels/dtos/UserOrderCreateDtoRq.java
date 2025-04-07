package ru.otus.java.pro.result.project.hotels.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.java.pro.result.project.hotels.validators.DateRangeValid;

import java.time.LocalDate;

@Schema(description = "Запрос бронирования жилья")
@Data
@NoArgsConstructor
@AllArgsConstructor
@DateRangeValid(before = "dateIn", after = "dateOut")
public class UserOrderCreateDtoRq {
    public static final String HOTEL_ID_FIELD = "hotelId";
    public static final String RATE_ID_FIELD = "roomId";
    public static final String ROOM_ID_FIELD = "rateId";
    public static final String DATE_IN_FIELD = "dateIn";
    public static final String DATE_OUT_FIELD = "dateOut";
    public static final String USER_ID_FIELD = "userId";

    @Schema(description = "Идентификатор отеля", type = "integer", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull (message = HOTEL_ID_FIELD + " is required")
    @Positive (message = HOTEL_ID_FIELD + " must be positive")
    private Integer hotelId;
    @Schema(description = "Идентификатор номера", type = "integer", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @NotNull (message = ROOM_ID_FIELD + " is required")
    @Positive (message = ROOM_ID_FIELD + " must be positive")
    private Integer roomId;
    @Schema(description = "Идентификатор тарифа", type = "integer", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull (message = RATE_ID_FIELD + " is required")
    @Positive (message = RATE_ID_FIELD + " must be positive")
    private Integer rateId;
    @Schema(description = "Идентификатор клиента", type = "integer", requiredMode = Schema.RequiredMode.REQUIRED, example = "00000001")
    @NotNull
    @Pattern(regexp = "\\d{8}", message = USER_ID_FIELD + " invalid, must consist of 8 digits")
    private String userId;
    @Schema(description = "Дата заезда", requiredMode = Schema.RequiredMode.REQUIRED, format = "date", type = "string", example = "2025-10-25")
    @NotNull
    @FutureOrPresent(message = DATE_IN_FIELD + " check-in date must be greater or equal to the current date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateIn;
    @Schema(description = "Дата выезда", requiredMode = Schema.RequiredMode.REQUIRED, format = "date", type = "string", example = "2025-10-26")
    @NotNull
    @Future(message = DATE_OUT_FIELD + " departure date must be greater to the current date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOut;
    @Schema(description = "Количество проживающих гостей", type = "integer", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @Positive
    @NotNull
    private Integer guests;
    @Schema(description = "Количество детей", type = "integer", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    @PositiveOrZero
    private Integer children;

}
