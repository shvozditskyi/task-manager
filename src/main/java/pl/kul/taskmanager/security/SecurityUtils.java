package pl.kul.taskmanager.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.kul.taskmanager.security.UserPrincipal;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    public static Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object var2 = authentication.getPrincipal();
            return ((UserPrincipal) var2).getId();
        }
        return -1L;
    }
}
