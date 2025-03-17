package ru.otus.java.pro.result.project.hotels.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_profile_id_gen")
    @SequenceGenerator(name = "user_profile_id_gen", sequenceName = "user_profile_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "login", length = 50)
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @ColumnDefault("''")
    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Column(name = "gender", nullable = false, length = 20)
    private String gender;

    @Column(name = "birthday_date")
    private LocalDate birthdayDate;

    @Column(name = "phone_number", length = 11)
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_send_notification")
    private Boolean isSendNotification;

    @Column(name = "bonus_value")
    private Integer bonusValue;

    @ColumnDefault("now()")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("now()")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "userProfile")
    private Set<UserOrder> userOrders = new LinkedHashSet<>();

}