package pl.kul.taskmanager.security.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USERS", uniqueConstraints = { @UniqueConstraint(columnNames = { "EMAIL" }) })
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;

    @Column(nullable = false)
    @NotBlank
    @Length(max = 255)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String passwd;

    @Column(nullable = false)
    @NotNull
    private Boolean enabled;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime creationDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"))
    private Set<RoleEntity> roleEntities = new HashSet<>();

    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "USER_DETAILS_ID", unique = true)
    private UserDetailsEntity userDetails;

    public void addRole(RoleEntity roleEntity) {
        roleEntities.add(roleEntity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, email);
    }
}