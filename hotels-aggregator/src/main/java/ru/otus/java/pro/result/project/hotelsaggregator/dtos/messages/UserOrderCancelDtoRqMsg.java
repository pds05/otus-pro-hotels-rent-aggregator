package ru.otus.java.pro.result.project.hotelsaggregator.dtos.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderCancelDtoRqMsg {

    private String order;
    private String login;
    private String password;

}
