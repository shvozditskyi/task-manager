package pl.kul.taskmanager.security.payload;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static pl.kul.taskmanager.commons.ValidationMessages.WRONG_SIZE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @Size(max = 250, message = WRONG_SIZE)
    private String email;

    @Size(max = 250, message = WRONG_SIZE)
    private String password;
}

