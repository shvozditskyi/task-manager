package pl.kul.taskmanager.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import pl.kul.taskmanager.security.dto.TokenDTO;
import pl.kul.taskmanager.security.repository.TokenRepository;
import pl.kul.taskmanager.security.jwt.JwtTokenProvider;
import pl.kul.taskmanager.security.mapper.TokenMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final JwtTokenProvider tokenProvider;
    private final TransactionTemplate transactionTemplate;

    @Override
    public boolean checkValid(String token) {
        TokenDTO tokenDTO = tokenRepository.findByTokenAndActive(token)
                .map(TokenMapper.MAPPER::toDTO)
                .orElse(null);
        if (tokenDTO == null || tokenDTO.getActive() == null || !tokenDTO.getActive()) {
            return false;
        }
        if (tokenDTO.getToken() != null && !tokenProvider.validateToken(token)) {
            transactionTemplate.executeWithoutResult(transactionStatus -> tokenRepository.revoke(token));
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
