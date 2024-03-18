package pl.kul.taskmanager.security.roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import pl.kul.taskmanager.security.permission.PermissionEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ROLES", uniqueConstraints = {@UniqueConstraint(columnNames = "NAME")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    private Long id;

    @Column(name = "NAME", length = 100, unique = true, nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", length = 100)
    private String description;

    @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST },
            targetEntity = PermissionEntity.class, fetch = FetchType.EAGER)
    @JoinTable(name = "PERMISSION_TO_ROLE",
            inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID"),
            joinColumns = @JoinColumn(name = "ROLE_ID"),
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Set<PermissionEntity> permissions = new HashSet<>();

}
