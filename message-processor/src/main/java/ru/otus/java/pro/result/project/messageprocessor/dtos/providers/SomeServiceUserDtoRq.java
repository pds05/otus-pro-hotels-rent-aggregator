package ru.otus.java.pro.result.project.messageprocessor.dtos.providers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SomeServiceUserDtoRq extends AbstractProviderDto {

    private String firstName;
    private String lastName;
    private String middleName;
    private String password;
    private String phoneNumber;
    private String email;

}