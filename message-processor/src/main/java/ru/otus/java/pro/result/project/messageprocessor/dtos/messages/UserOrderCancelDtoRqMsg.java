package ru.otus.java.pro.result.project.messageprocessor.dtos.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderCancelDtoRqMsg extends AbstractMessageDto {

    private String order;
    private String login;
    private String password;

}
