package ru.otus.java.pro.result.project.hotelsaggregator.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.java.pro.result.project.hotelsaggregator.validators.UsernameValid;

@Schema(description = "Запрос регистрации нового пользователя")
@UsernameValid
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRq {
    public static final String FIRST_NAME_FIELD = "firstName";
    public static final String LAST_NAME_FIELD = "lastName";
    public static final String MIDDLE_NAME_FIELD = "middleName";
    public static final String EMAIL_FIELD = "email";
    public static final String PHONE_NUMBER_FIELD = "phoneNumber";
    public static final String PASSWORD_FIELD = "password";
    @Schema(description = "Имя пользователя", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = FIRST_NAME_FIELD + " is required")
    private String firstName;
    @Schema(description = "Фамилия пользователя", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = LAST_NAME_FIELD + " is required")
    private String lastName;
    @Schema(description = "Отчество пользователя", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String middleName;
    @Schema(description = "Адрес электронной почты", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Email
    private String email;
    @Schema(description = "Номер телефона, присваивается username", pattern = "7\\d{10}", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Pattern(regexp = "7\\d{10}", message = PHONE_NUMBER_FIELD + " must consist of 10 digits and start with 7")
    private String phoneNumber;
    @Schema(description = "Пароль доступа. Присваивается username, если отсутствует email", format = "password", requiredMode = Schema.RequiredMode.REQUIRED, minLength = 8, maxLength = 255, example = "Pass1234")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = PASSWORD_FIELD + " must consist of at least eight characters, at least one uppercase letter, one lowercase letter and one digit")
    @NotBlank(message = PASSWORD_FIELD + " is required")
    private String password;

}
