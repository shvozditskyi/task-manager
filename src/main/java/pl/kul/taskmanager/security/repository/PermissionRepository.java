package pl.kul.taskmanager.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kul.taskmanager.security.entity.PermissionEntity;

import java.util.Set;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {

    Set<PermissionEntity> findByRoleEntitiesNameIn(Set<String> roles);
}
