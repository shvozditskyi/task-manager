package pl.kul.taskmanager.security.permission;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.hibernate.type.YesNoConverter;
import pl.kul.taskmanager.security.roles.RoleEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PERMISSIONS", uniqueConstraints = {@UniqueConstraint(columnNames = "NAME")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_seq")
    private Long id;

    @Column(name = "NAME", length = 100, unique = true, nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", length = 100)
    private String description;

    @Column(name = "ACTIVE", nullable = false)
    @Convert(converter = YesNoConverter.class)
    private Boolean active = Boolean.FALSE;

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST },
            targetEntity = RoleEntity.class, fetch = FetchType.EAGER)
    @JoinTable(name = "PERMISSION_TO_ROLE",
            joinColumns = @JoinColumn(name = "PERMISSION_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"),
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Set<RoleEntity> roleEntities = new HashSet<>();

    @PreRemove
    private void removePermissionsFromRoles() {
        for (RoleEntity r : roleEntities) {
            r.getPermissions().remove(this);
        }
    }
}
