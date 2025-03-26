package ru.otus.java.pro.result.project.hotels.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.java.pro.result.project.hotels.entities.UserProfile;

import java.util.function.Function;

@Schema( description = "Профиль пользователя")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    @Schema(description = "Идентификатор профиля")
    private String id;
    @Schema(description = "Наименование учетной записи (email или номер телефона)")
    private String username;
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

    public static final Function<UserProfile, UserDto> ENTITY_USER_TO_DTO = user -> new UserDto(
            user.getId(),
            user.getLogin(),
            user.getFirstName(),
            user.getLastName(),
            user.getMiddleName(),
            user.getEmail(),
            user.getPhoneNumber());

    public static UserDto mapping(UserProfile userProfile) {
        return ENTITY_USER_TO_DTO.apply(userProfile);
    }
}
