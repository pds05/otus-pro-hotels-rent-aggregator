package ru.otus.java.pro.result.project.messageprocessor.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProviderResponseDto {
    private String providerName;
    private Object data;
}
