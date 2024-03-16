package pl.kul.taskmanager.security.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignupRequest {
    @NotBlank
    @Size(min = 4, max = 100)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

}
