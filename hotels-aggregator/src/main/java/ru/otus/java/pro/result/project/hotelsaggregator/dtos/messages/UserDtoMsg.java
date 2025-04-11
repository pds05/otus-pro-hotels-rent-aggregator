package ru.otus.java.pro.result.project.hotelsaggregator.dtos.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoMsg {

    private String id;
    private String login;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phoneNumber;

}
