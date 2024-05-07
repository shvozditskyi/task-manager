package pl.kul.taskmanager.user.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kul.taskmanager.user.entity.UserDetailsEntity;
import pl.kul.taskmanager.user.repository.UserDetailsRepository;
import pl.kul.taskmanager.user.repository.UserRepository;

import static pl.kul.taskmanager.security.SecurityUtils.getUserId;

@Component
@RequiredArgsConstructor
public class UserUtils {

    private final UserDetailsRepository userDetailsRepository;
    private final UserRepository userRepository;

    public UserDetailsEntity findByUserId(Long userDetailsId) {
        return userDetailsRepository.findByUserId(userDetailsId).orElseThrow(
                () -> new RuntimeException("User not found")
        );
    }

    public UserDetailsEntity findByEmail(String email) {
        return userDetailsRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Long getIdByEmail(String email) {
        return userDetailsRepository.findByEmail(email)
                .map(UserDetailsEntity::getId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserDetailsEntity getLoggedUserDetails(){
        Long userId = getUserId();
        return userDetailsRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }
}

