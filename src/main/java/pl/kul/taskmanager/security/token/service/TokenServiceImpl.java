package pl.kul.taskmanager.security.token.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kul.taskmanager.security.token.TokenEntity;
import pl.kul.taskmanager.security.auth.jwt.JwtTokenProvider;
import pl.kul.taskmanager.security.token.TokenRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final JwtTokenProvider tokenProvider;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean checkValid(String token) {
        TokenEntity tokenEntity = tokenRepository.findByTokenAndActive(token)
                .orElse(null);
        if (tokenEntity == null || tokenEntity.getIsActive() == null || !tokenEntity.getIsActive()) {
            return false;
        }
        if (tokenEntity.getToken() != null && !tokenProvider.validateToken(token)) {
            tokenRepository.revoke(token);
            return false;
        }
        return true;
    }

    @Override
    public void revoke(String token) {
        tokenRepository.revoke(token);
    }
}
