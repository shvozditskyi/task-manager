package pl.kul.taskmanager.security.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.YesNoConverter;

import java.time.LocalDateTime;

@Entity
@Table(name = "TOKENS")
@Getter
@Setter
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;

    @Column(name = "TOKEN", unique = true)
    private String token;

    @Column(name = "ACTIVE", nullable = false)
    @Convert(converter = YesNoConverter.class)
    private Boolean active = Boolean.TRUE;

    @Column(name = "CREATION_DATE", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime creationDate;

    @Column(name = "EXPIRATION_DATE", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime expirationDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
}
