package pl.kul.taskmanager.security.service.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kul.taskmanager.security.dto.TokenDTO;
import pl.kul.taskmanager.security.repository.TokenRepository;
import pl.kul.taskmanager.security.jwt.JwtTokenProvider;
import pl.kul.taskmanager.security.mapper.TokenMapper;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final JwtTokenProvider tokenProvider;

    @Override
    @Transactional
    public boolean checkValid(String token) {
        TokenDTO tokenDTO = tokenRepository.findByTokenAndActive(token)
                .map(TokenMapper.MAPPER::toDTO)
                .orElse(null);
        if (tokenDTO == null || tokenDTO.getActive() == null || !tokenDTO.getActive()) {
            return false;
        }
        if (tokenDTO.getToken() != null && !tokenProvider.validateToken(token)) {
            tokenRepository.revoke(token);
            tokenDTO.setActive(false);
            return false;
        }
        return true;
    }

    @Override
    public void revoke(String token) {
        tokenRepository.revoke(token);
    }
}
