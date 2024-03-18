package pl.kul.taskmanager.security.permission.service;

import pl.kul.taskmanager.security.permission.PermissionEntity;

import java.util.Set;

public interface PermissionService {
    Set<PermissionEntity> getActivePermissionsForRoles(Set<String> roles);
}
