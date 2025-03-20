package ru.otus.java.pro.result.project.hotels.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @NotBlank (message = FIRST_NAME_FIELD + " is required")
    private String firstName;
    @NotBlank (message = LAST_NAME_FIELD + " is required")
    private String lastName;

    private String middleName;
    @Email
    private String email;
    @Pattern(regexp = "7\\d{10}", message = PHONE_NUMBER_FIELD + " must consist of 10 digits and start with 7")
    private String phoneNumber;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = PASSWORD_FIELD + " must consist of at least eight characters, at least one uppercase letter, one lowercase letter and one digit")
    private String password;
}
