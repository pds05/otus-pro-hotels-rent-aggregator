package ru.otus.java.pro.result.project.messageprocessor.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.otus.java.pro.result.project.messageprocessor.enums.BusinessMethodEnum;

@Getter
@Setter
@Entity
@Table(name = "provider_apis")
public class ProviderApi {
    @Id
    @ColumnDefault("nextval('provider_apis_id_seq')")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "rest_method", length = 6)
    private String restMethod;

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

    public String createTopicName(String directionSuffix) {
        StringBuilder builder = new StringBuilder();
        builder.append(provider.getPropertyName())
                .append("_")
                .append(businessMethod.name().toLowerCase());
        if (directionSuffix != null && !directionSuffix.isEmpty()) {
            builder.append("_").append(directionSuffix.toLowerCase());
        }
        return builder.toString();
    }

}