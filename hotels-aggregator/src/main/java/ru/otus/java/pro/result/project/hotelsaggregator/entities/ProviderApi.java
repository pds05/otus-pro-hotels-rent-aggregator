package ru.otus.java.pro.result.project.hotelsaggregator.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "provider_apis")
public class ProviderApi {
    @Id
    @ColumnDefault("nextval('provider_apis_id_seq')")
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "rest_method", length = 6)
    private String restMethod;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "business_method", nullable = false)
    private BusinessMethodEnum businessMethod;

    @Column(name = "description")
    private String description;

    @Column(name = "response_template")
    private String responseTemplate;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "provider_id")
    private Provider provider;

}