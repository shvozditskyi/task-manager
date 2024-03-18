package pl.kul.taskmanager.security.permission.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kul.taskmanager.security.permission.PermissionEntity;
import pl.kul.taskmanager.security.permission.PermissionRepository;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public Set<PermissionEntity> getActivePermissionsForRoles(Set<String> roles) {
        return permissionRepository.findByRoleNameIn(roles);
    }
}
