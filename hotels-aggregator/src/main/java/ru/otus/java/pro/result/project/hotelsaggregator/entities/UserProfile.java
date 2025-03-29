package ru.otus.java.pro.result.project.hotelsaggregator.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ToString(exclude = "userOrders")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 8)
    private String id;

    @NotEmpty
    @Column(name = "login", length = 50)
    private String login;

    @NotEmpty
    @Column(name = "password")
    private String password;

    @NotEmpty
    @NotNull
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotEmpty
    @NotNull
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Column(name = "birthday_date")
    private LocalDate birthdayDate;

    @Length(min = 11, max = 11)
    @Column(name = "phone_number", length = 11)
    private String phoneNumber;

    @Email
    @Column(name = "email")
    private String email;

    @ColumnDefault("true")
    @Column(name = "is_active")
    private Boolean isActive;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = UserOrder_.USER_PROFILE)
    private Set<UserOrder> userOrders;

    public String printFullName(){
        return Stream.of(lastName, firstName, middleName).filter(Objects::nonNull).collect(Collectors.joining( " "));
    }

}