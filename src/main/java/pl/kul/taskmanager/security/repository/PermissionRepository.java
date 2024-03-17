package pl.kul.taskmanager.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kul.taskmanager.security.entity.PermissionEntity;

import java.util.Set;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {

    @Query("SELECT p FROM PermissionEntity p JOIN p.roleEntities r WHERE r.name IN :roles")
    Set<PermissionEntity> findByRoleNameIn(Set<String> roles);
}
