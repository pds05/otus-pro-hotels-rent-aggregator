package ru.otus.java.pro.result.project.messageprocessor.dtos.providers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SomeServiceHotelsDto extends AbstractProviderDto {

    private List<SomeServiceHotelDto> hotels;

}
