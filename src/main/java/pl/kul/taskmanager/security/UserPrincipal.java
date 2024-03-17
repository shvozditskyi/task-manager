package pl.kul.taskmanager.security;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.kul.taskmanager.security.entity.RoleEntity;
import pl.kul.taskmanager.security.entity.UserEntity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPrincipal implements UserDetails {

    private Long id;

    private String email;

    private String password;

    private Boolean enabled;

    private Collection<GrantedAuthority> authorities;

    private Set<String> roles;

    public static UserPrincipal create(UserEntity user, List<GrantedAuthority> authorities) {
        return UserPrincipal.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .enabled(user.getEnabled())
                .authorities(authorities)
                .roles(user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet()))
                .build();
    }

    @JsonGetter("authorities")
    public Set<String> getSimpleAuthorities() {
        if (CollectionUtils.isEmpty(authorities)) {
            return new HashSet<>();
        }
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }

    @JsonSetter("authorities")
    public void setSimpleAuthorities(Set<String> authorities) {
        setAuthorities(authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
    }

    @JsonGetter("email")
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
