package pl.kul.taskmanager.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.kul.taskmanager.security.dto.UserDetailsDTO;
import pl.kul.taskmanager.security.repository.UserRepository;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return null;
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
