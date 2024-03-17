package pl.kul.taskmanager.security.service.permission;

import pl.kul.taskmanager.security.entity.PermissionEntity;

import java.util.Set;

public interface PermissionService {
    Set<PermissionEntity> getActivePermissionsForRoles(Set<String> roles);
}
