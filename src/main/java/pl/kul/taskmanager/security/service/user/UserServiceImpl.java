package pl.kul.taskmanager.security.service.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.kul.taskmanager.security.UserPrincipal;
import pl.kul.taskmanager.security.dto.UserDetailsDTO;
import pl.kul.taskmanager.security.entity.RoleEntity;
import pl.kul.taskmanager.security.entity.UserEntity;
import pl.kul.taskmanager.security.repository.UserRepository;
import pl.kul.taskmanager.security.service.permission.PermissionService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PermissionService permissionService;

    private static final String ROLE_PREFIX = "ROLE_";

    @Override
    @Transactional(rollbackOn = Exception.class)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         UserEntity userEntity = repository.findByEmail(email)
                     .orElseThrow(
                             () -> new UsernameNotFoundException("UserEntity not found with email : " + email));
         return getUserPrincipal(userEntity);
    }

    private UserDetails getUserPrincipal(UserEntity user) {
        Set<String> roles = user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet());
        List<GrantedAuthority> authorities = new ArrayList<>(getAuthoritiesForRoles(roles));
        return UserPrincipal.create(user, authorities);
    }

    private Collection<GrantedAuthority> getAuthoritiesForRoles(Set<String> roles) {
        return permissionService.getActivePermissionsForRoles(roles)
                .stream()
                .map(p -> ROLE_PREFIX + p.getName())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getCurrentUserPermissions() {
        return null;
    }

    @Override
    public UserDetailsDTO getUserInfo(String login) {
        return null;
    }
}
