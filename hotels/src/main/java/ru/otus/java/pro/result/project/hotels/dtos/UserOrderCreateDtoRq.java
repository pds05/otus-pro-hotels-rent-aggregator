package ru.otus.java.pro.result.project.hotels.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.java.pro.result.project.hotels.validators.DateRangeValid;

import java.time.LocalDate;

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

    @NotNull (message = HOTEL_ID_FIELD + " is required")
    @Positive (message = HOTEL_ID_FIELD + " must be positive")
    private Integer hotelId;
    @NotNull (message = ROOM_ID_FIELD + " is required")
    @Positive (message = ROOM_ID_FIELD + " must be positive")
    private Integer roomId;
    @NotNull (message = RATE_ID_FIELD + " is required")
    @Positive (message = RATE_ID_FIELD + " must be positive")
    private Integer rateId;
    @Pattern(regexp = "\\d{8}", message = USER_ID_FIELD + " invalid, must consist of 8 digits")
    private String userId;
    @FutureOrPresent(message = DATE_IN_FIELD + " check-in date must be greater or equal to the current date")
    private LocalDate dateIn;
    @Future(message = DATE_OUT_FIELD + " departure date must be greater to the current date")
    private LocalDate dateOut;


}
