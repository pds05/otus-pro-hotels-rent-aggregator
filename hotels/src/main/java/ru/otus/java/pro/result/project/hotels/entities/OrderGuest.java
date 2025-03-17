package ru.otus.java.pro.result.project.hotels.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @ColumnDefault("''")
    @Column(name = "middle_name", length = 50)
    private String middleName;

    @ColumnDefault("false")
    @Column(name = "is_child")
    private Boolean isChild;

    @Column(name = "child_age")
    private Short childAge;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_order_id", nullable = false)
    private UserOrder userOrder;

}