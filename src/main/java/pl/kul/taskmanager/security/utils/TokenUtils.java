package pl.kul.taskmanager.security.utils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenUtils {
    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";

    public static String extractToken(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }
}
