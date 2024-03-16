package pl.kul.taskmanager.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.kul.taskmanager.security.dto.UserDetailsDTO;

import java.util.Set;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String login) throws UsernameNotFoundException;

    Set<String> getCurrentUserPermissions();
    UserDetailsDTO getUserInfo(String login);

}
