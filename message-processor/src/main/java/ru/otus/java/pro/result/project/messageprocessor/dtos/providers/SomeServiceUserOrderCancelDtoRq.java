package ru.otus.java.pro.result.project.messageprocessor.dtos.providers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SomeServiceUserOrderCancelDtoRq extends AbstractProviderDto {

    private String order;

}
