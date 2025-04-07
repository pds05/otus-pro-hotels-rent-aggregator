package ru.otus.java.pro.result.project.hotels.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema( description = "Профиль пользователя")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @Schema(description = "Идентификатор профиля")
    private String id;
    @Schema(description = "Наименование учетной записи (email или номер телефона)")
    private String login;
    @Schema(description = "Имя пользователя")
    private String firstName;
    @Schema(description = "Фамилия пользователя")
    private String lastName;
    @Schema(description = "Отчество пользователя", nullable = true)
    private String middleName;
    @Schema(description = "Электронная почта", nullable = true)
    private String email;
    @Schema(description = "Номер телефона", nullable = true)
    private String phoneNumber;

}
