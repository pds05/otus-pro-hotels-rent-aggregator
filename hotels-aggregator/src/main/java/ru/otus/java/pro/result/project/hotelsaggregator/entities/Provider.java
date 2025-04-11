package ru.otus.java.pro.result.project.hotelsaggregator.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.*;
import ru.otus.java.pro.result.project.hotelsaggregator.enums.BusinessMethodEnum;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

import static org.hibernate.type.SqlTypes.INTERVAL_SECOND;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "providers")
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "providers_id_gen")
    @SequenceGenerator(name = "providers_id_gen", sequenceName = "providers_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotEmpty
    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "property_name")
    private String propertyName;

    @NotNull
    @Column(name = "api_url", nullable = false)
    private String apiUrl;

    @Column(name = "api_login", length = 50)
    private String apiLogin;

    @Column(name = "api_password")
    private String apiPassword;

    @ColumnDefault("true")
    @Column(name = "is_active")
    private Boolean isActive;

    @JdbcTypeCode(INTERVAL_SECOND)
    @Column(name = "read_timeout")
    private Duration readTimeout;

    @JdbcTypeCode(INTERVAL_SECOND)
    @Column(name = "connect_timeout")
    private Duration connectTimeout;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = ProviderApi_.PROVIDER)
    private Set<ProviderApi> providerApis;

    public ProviderApi getProviderApi(BusinessMethodEnum businessMethod) {
        return providerApis.stream().filter(api -> api.getBusinessMethod().equals(businessMethod)).findFirst().orElse(null);
    }

}