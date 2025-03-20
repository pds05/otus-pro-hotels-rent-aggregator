package ru.otus.java.pro.result.project.hotels.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderKey implements Serializable {
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "user_profile_id")
    private String userProfileId;

    public String print() {
        return String.format("%s-%s", userProfileId, orderId);
    }

}
