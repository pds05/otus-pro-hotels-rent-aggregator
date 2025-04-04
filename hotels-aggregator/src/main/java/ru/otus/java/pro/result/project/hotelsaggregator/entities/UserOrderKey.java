package ru.otus.java.pro.result.project.hotelsaggregator.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Embeddable
public class UserOrderKey implements java.io.Serializable {
    private static final long serialVersionUID = 385005079588784478L;
    @NotNull
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Size(min= 10, max = 10)
    @NotEmpty
    @Column(name = "user_profile_id", nullable = false, length = 10)
    private String userProfileId;

    public String print() {
        return String.format("%s-%s", userProfileId, orderId);
    }
}