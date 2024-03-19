package pl.kul.taskmanager.security.auth;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.kul.taskmanager.security.UserPrincipal;
import pl.kul.taskmanager.user.UserDetailsDTO;
import pl.kul.taskmanager.security.roles.RoleEntity;
import pl.kul.taskmanager.security.token.TokenEntity;
import pl.kul.taskmanager.user.entity.UserDetailsEntity;
import pl.kul.taskmanager.user.entity.UserEntity;
import pl.kul.taskmanager.security.roles.RolesRepository;
import pl.kul.taskmanager.security.token.TokenRepository;
import pl.kul.taskmanager.security.auth.jwt.JwtTokenProvider;
import pl.kul.taskmanager.security.auth.dto.JwtAuthenticationResponse;
import pl.kul.taskmanager.security.auth.dto.LoginRequest;
import pl.kul.taskmanager.user.repository.UserDetailsRepository;
import pl.kul.taskmanager.user.repository.UserRepository;
import pl.kul.taskmanager.user.service.UserService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthenticationContext {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final TokenRepository tokenRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String ROLE_USER = "ROLE_USER";

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public Authentication authenticate(LoginRequest loginRequest) {
        return this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
    }

    @Transactional
    public void createAccount(UserDetailsDTO request) {
        String requestEmail = request.getEmail();
        if(userRepository.findByEmail(requestEmail).isPresent()){
            throw new UsernameNotFoundException("User with email: " + requestEmail + " already exists");
        }
        saveUserEntity(request);
    }

    @Transactional
    public JwtAuthenticationResponse getAuthenticationResponse(LoginRequest loginRequest) {
        Authentication authentication = this.authenticate(loginRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = tokenProvider.generateToken(authentication);
        saveToken(jwtToken, loginRequest.getEmail(), jwtExpirationInMs);
        return new JwtAuthenticationResponse(jwtToken);
    }

    private void saveToken(String jwtToken, @NotBlank String email, int jwtExpirationInMs) {
        UserPrincipal userPrincipal = (UserPrincipal) userService.loadUserByUsername(email);
        tokenRepository.findByUserId(userPrincipal.getId()).ifPresent(tokenRepository::revoke);
        TokenEntity token = TokenEntity.builder()
                 .user(userRepository.findById(userPrincipal.getId()).orElse(null))
                 .token(jwtToken)
                 .creationDate(LocalDateTime.now())
                 .isActive(true)
                 .expirationDate(LocalDateTime.now().plusSeconds(jwtExpirationInMs / 1000))
                 .build();
        tokenRepository.save(token);
    }

    private void saveUserEntity(UserDetailsDTO request) {
        Set<RoleEntity> roles = rolesRepository.findByName(ROLE_USER).map(Set::of).orElse(new HashSet<>());
        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .creationDate(LocalDateTime.now())
                .enabled(true)
                .build();
        userRepository.save(user);
        saveUserDetailsEntity(request, user);
    }

    private UserDetailsEntity saveUserDetailsEntity(UserDetailsDTO request, UserEntity userEntity) {
        UserDetailsEntity userDetails = UserDetailsEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .user(userEntity)
                .build();
        return userDetailsRepository.save(userDetails);
    }
}
