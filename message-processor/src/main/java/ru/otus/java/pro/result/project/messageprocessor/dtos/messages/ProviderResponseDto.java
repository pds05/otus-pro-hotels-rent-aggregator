package ru.otus.java.pro.result.project.messageprocessor.dtos.messages;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProviderResponseDto extends InternalDto {

    private String providerName;

    private Object data;
}
