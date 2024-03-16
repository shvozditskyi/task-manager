package pl.kul.taskmanager.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDTO {
    private Long id;
    private String email;
    private Boolean active;
    private int validityTime;
    private String token;
}
