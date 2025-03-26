package ru.otus.java.pro.result.project.hotels.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderKey implements Serializable {
    @NotNull
    @Column(name = "order_id")
    private Integer orderId;

    @Size(min = 8, max = 8)
    @NotEmpty
    @Column(name = "user_profile_id")
    private String userProfileId;

    public String print() {
        return String.format("%s-%s", userProfileId, orderId);
    }

}
