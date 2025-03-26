package ru.otus.java.pro.result.project.hotels.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@ToString(exclude = "userOrder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_guests")
public class OrderGuest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_guests_id_gen")
    @SequenceGenerator(name = "order_guests_id_gen", sequenceName = "order_guests_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotEmpty
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @ColumnDefault("false")
    @Column(name = "is_child")
    private Boolean isChild;

    @Column(name = "child_age")
    private Short childAge;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumns(value = {
            @JoinColumn(name = "user_order_id", nullable = false),
            @JoinColumn(name = "user_profile_id", nullable = false)
    })
    private UserOrder userOrder;

}