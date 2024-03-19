package pl.kul.taskmanager.security.auth;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.kul.taskmanager.security.user.UserDetailsDTO;
import pl.kul.taskmanager.security.auth.jwt.JwtTokenProvider;
import pl.kul.taskmanager.security.auth.dto.JwtAuthenticationResponse;
import pl.kul.taskmanager.security.auth.dto.LoginRequest;
import pl.kul.taskmanager.security.token.service.TokenService;
import pl.kul.taskmanager.security.token.TokenUtils;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider tokenProvider;
    private final TokenService tokenService;

    @Autowired
    private AuthenticationContext authenticationContext;

    @PostMapping(value = "/register")
    public ResponseEntity<Map<String, String>> createAccount(@Valid @RequestBody UserDetailsDTO signupRequest) {
        authenticationContext.createAccount(signupRequest);
        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationContext.getAuthenticationResponse(loginRequest));
    }

    @PostMapping(value = "/checkValid")
    public ResponseEntity<Boolean> checkValid(@RequestParam("Authorization") String token) {
        return ResponseEntity.ok(tokenService.checkValid(TokenUtils.extractToken(token)));
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Void> deauthenticateUser(@RequestHeader("Authorization") String token) {
        tokenService.revoke(TokenUtils.extractToken(token));
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/extractLogin")
    public ResponseEntity<String> extractUserLogin(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(tokenProvider.getEmailFromJWT(TokenUtils.extractToken(token)));
    }

}