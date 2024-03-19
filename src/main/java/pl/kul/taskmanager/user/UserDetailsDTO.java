package pl.kul.taskmanager.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static pl.kul.taskmanager.commons.ValidationMessages.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDetailsDTO {
    @NotBlank(message = EMAIL_IS_REQUIRED)
    @Size(min = 4, max = 100, message = EMAIL_SIZE)
    @Email(message = EMAIL_IS_NOT_VALID)
    private String email;

    @NotBlank(message = PASSWORD_IS_REQUIRED)
    @Size(min = 6, max = 30, message = PASSWORD_SIZE)
    @Pattern(regexp = PASSWORD_REGEXP, message = PASSWORD_IS_NOT_VALID)
    private String password;

    @NotBlank(message = FIRST_NAME_IS_REQUIRED)
    @Size(max = 50, message = FIRST_NAME_SIZE)
    @Pattern(regexp = FIRST_NAME_REGEXP, message = FIRST_NAME_IS_NOT_VALID)
    private String firstName;

    @Size(max = 50, message = LAST_NAME_SIZE)
    @Pattern(regexp = LAST_NAME_REGEXP, message = LAST_NAME_IS_NOT_VALID)
    private String lastName;

    @Size(min = 9, max = 13, message = PHONE_NUMBER_SIZE)
    @Pattern(regexp = PHONE_NUMBER_REGEXP, message = PHONE_NUMBER_IS_NOT_VALID)
    private String phoneNumber;
}
