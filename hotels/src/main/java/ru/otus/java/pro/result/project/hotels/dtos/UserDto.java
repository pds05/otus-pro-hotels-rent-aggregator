package ru.otus.java.pro.result.project.hotels.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.java.pro.result.project.hotels.entities.UserProfile;

import java.util.function.Function;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phone;

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
