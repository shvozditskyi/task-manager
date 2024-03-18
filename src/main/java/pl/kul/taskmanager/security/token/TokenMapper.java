package pl.kul.taskmanager.security.token;

import org.springframework.stereotype.Component;

@Component
public class TokenMapper {

    public static TokenDTO toDTO(TokenEntity tokenEntity) {
        return TokenDTO.builder()
                .id(tokenEntity.getId())
                .token(tokenEntity.getToken())
                .active(tokenEntity.getActive())
                .build();
    }

    public static TokenDTO toEntity(TokenEntity tokenEntity) {
        return TokenDTO.builder()
                .id(tokenEntity.getId())
                .email(tokenEntity.getUser().getEmail())
                .token(tokenEntity.getToken())
                .active(tokenEntity.getActive())
                .build();
    }
}
