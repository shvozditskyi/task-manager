package pl.kul.taskmanager.security.token;

import org.springframework.stereotype.Component;

@Component
public class TokenMapper {

    public static TokenDTO toDTO(TokenEntity tokenEntity) {
        return TokenDTO.builder()
                .id(tokenEntity.getId())
                .token(tokenEntity.getToken())
                .isActive(tokenEntity.getIsActive())
                .build();
    }

    public static TokenDTO toEntity(TokenEntity tokenEntity) {
        return TokenDTO.builder()
                .id(tokenEntity.getId())
                .email(tokenEntity.getUser().getEmail())
                .token(tokenEntity.getToken())
                .isActive(tokenEntity.getIsActive())
                .build();
    }
}
