package ru.otus.java.pro.result.project.messageprocessor.dtos.messages;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderCreateDtoRqMsg extends AbstractMessageDto {

    private Integer hotelId;
    private Integer roomId;
    private Integer rateId;
    private String userId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateIn;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOut;
    private Integer guests;
    private Integer children;

}
