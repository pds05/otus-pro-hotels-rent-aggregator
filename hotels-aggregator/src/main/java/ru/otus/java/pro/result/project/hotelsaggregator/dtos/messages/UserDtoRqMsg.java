package ru.otus.java.pro.result.project.hotelsaggregator.dtos.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.java.pro.result.project.hotelsaggregator.validators.UsernameValid;

@UsernameValid
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRqMsg {

    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phoneNumber;
    private String password;

}
