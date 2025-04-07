package ru.otus.java.pro.result.project.messageprocessor.dtos.providers;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SomeServiceUserOrderDto extends AbstractProviderDto {

    private String status;
    private String order;
    private String providerOrder;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOrdered;
    private BigDecimal price;
    private String clientName;
    private String hotel;
    private String address;
    private String room;
    private String rate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateIn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOut;
    private Long nights;
    private String description;

}
