package pl.kul.taskmanager.user.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kul.taskmanager.user.entity.UserDetailsEntity;
import pl.kul.taskmanager.user.repository.UserDetailsRepository;

@Component
@RequiredArgsConstructor
public class UserUtils {

    private final UserDetailsRepository userDetailsRepository;

    public UserDetailsEntity findUserDetailsByUserId(Long userDetailsId) {
        return userDetailsRepository.findById(userDetailsId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}

