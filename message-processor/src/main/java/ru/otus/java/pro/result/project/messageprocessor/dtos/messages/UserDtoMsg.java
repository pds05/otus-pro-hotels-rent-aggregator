package ru.otus.java.pro.result.project.messageprocessor.dtos.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoMsg extends AbstractMessageDto {

    private String id;
    private String login;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phoneNumber;

}
