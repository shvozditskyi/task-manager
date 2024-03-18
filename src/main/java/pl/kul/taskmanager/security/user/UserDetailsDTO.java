package pl.kul.taskmanager.security.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDetailsDTO {
    @NotBlank(message = "Email is required")
    @Size(min = 4, max = 100, message = "Email must contain from 4 to 100 characters")
    @Email(message = "Email is not valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 30, message = "Password must contain from 6 to 30 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$",
            message = "Password must contain at least one digit, one lowercase and one uppercase letter")
    private String password;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must contain from 2 to 50 characters")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "First name must contain only letters")
    private String firstName;

    @Size(max = 50, message = "Last name must contain up to 50 characters")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Last name must contain only letters")
    private String lastName;
}
