package pl.kul.taskmanager.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.kul.taskmanager.user.UserDetailsDTO;

import java.util.Set;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String login) throws UsernameNotFoundException;

    Set<String> getCurrentUserPermissions();
    UserDetailsDTO getUserInfo(String login);

}
