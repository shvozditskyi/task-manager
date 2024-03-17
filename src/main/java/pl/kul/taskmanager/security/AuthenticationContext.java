package pl.kul.taskmanager.security;

import jakarta.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.kul.taskmanager.security.entity.TokenEntity;
import pl.kul.taskmanager.security.repository.TokenRepository;
import pl.kul.taskmanager.security.jwt.JwtTokenProvider;
import pl.kul.taskmanager.security.payload.JwtAuthenticationResponse;
import pl.kul.taskmanager.security.payload.LoginRequest;
import pl.kul.taskmanager.security.repository.UserRepository;
import pl.kul.taskmanager.security.service.user.UserService;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AuthenticationContext {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final TokenRepository tokenValidityRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public Authentication authenticate(LoginRequest loginRequest) {
        return this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
    }

    public JwtAuthenticationResponse getAuthenticationResponse(LoginRequest loginRequest) {
        // todo: add validation
        Authentication authentication = this.authenticate(loginRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = tokenProvider.generateToken(authentication);
        saveToken(jwtToken, loginRequest.getEmail(), jwtExpirationInMs);
        return new JwtAuthenticationResponse(jwtToken);
    }

    private void saveToken(String jwtToken, @NotBlank String email, int jwtExpirationInMs) {
        UserPrincipal userPrincipal = (UserPrincipal) userService.loadUserByUsername(email);
        TokenEntity token = new TokenEntity();
        token.setUser(userRepository.findById(userPrincipal.getId()).orElse(null));
        token.setToken(jwtToken);
        token.setExpirationDate(LocalDateTime.now().plusSeconds(jwtExpirationInMs / 1000));
        tokenValidityRepository.save(token);
    }

}
