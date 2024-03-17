package pl.kul.taskmanager.security.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDetailsDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
