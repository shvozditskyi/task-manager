package pl.kul.taskmanager.user.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kul.taskmanager.user.entity.UserDetailsEntity;
import pl.kul.taskmanager.user.repository.UserDetailsRepository;

@Component
@RequiredArgsConstructor
public class UserUtils {

    private final UserDetailsRepository userDetailsRepository;

    public UserDetailsEntity findByUserId(Long userDetailsId) {
        return userDetailsRepository.findById(userDetailsId)
                .orElseThrow(() -> new RuntimeException("User not found"));
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
}

