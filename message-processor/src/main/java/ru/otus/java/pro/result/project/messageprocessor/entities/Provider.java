package ru.otus.java.pro.result.project.messageprocessor.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

import static org.hibernate.type.SqlTypes.INTERVAL_SECOND;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "property_name")
    private String propertyName;

    @Column(name = "api_url", nullable = false)
    private String apiUrl;

    @Column(name = "api_login", length = 50)
    private String apiLogin;

    @Column(name = "api_password")
    private String apiPassword;

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

}