package pl.kul.taskmanager.security;

import jakarta.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.kul.taskmanager.entity.user.TokenEntity;
import pl.kul.taskmanager.mapper.token.TokenMapper;
import pl.kul.taskmanager.payload.JwtAuthenticationResponse;
import pl.kul.taskmanager.payload.LoginRequest;
import pl.kul.taskmanager.repository.TokenValidityRepository;
import pl.kul.taskmanager.security.jwt.JwtTokenProvider;
import pl.kul.taskmanager.service.token.TokenCacheService;
import pl.kul.taskmanager.service.user.UserService;
import pl.kul.taskmanager.validator.LoginRequestValidator;

@Component
@RequiredArgsConstructor
public class AuthenticationContext {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final TokenValidityRepository tokenValidityRepository;
    private final UserService userService;
    private final TokenCacheService tokenCacheService;
    private final LoginRequestValidator validator;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public Authentication authenticate(LoginRequest loginRequest) {
        return this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));
    }

    public JwtAuthenticationResponse getAuthenticationResponse(LoginRequest loginRequest) {
        validator.validate(loginRequest);
        tokenProvider.processTaxusToken(loginRequest);
        Authentication authentication = this.authenticate(loginRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = tokenProvider.generateToken(authentication);
        saveToken(jwtToken, loginRequest.getLogin(), jwtExpirationInMs);
        return new JwtAuthenticationResponse(jwtToken);
    }

    private void saveToken(String jwtToken, @NotBlank String login, int jwtExpirationInMs) {
        UserPrincipal userPrincipal = (UserPrincipal) userService.loadUserByUsername(login);
        TokenEntity token = new TokenEntity();
        token.setUser(userService.getReference(userPrincipal.getId()));
        token.setToken(jwtToken);
        token.setValidityTime(jwtExpirationInMs);
        tokenValidityRepository.save(token);
        tokenCacheService.setToken(TokenMapper.MAPPER.toDTO(token, login));
    }

}
