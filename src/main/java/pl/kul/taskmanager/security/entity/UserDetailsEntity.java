package pl.kul.taskmanager.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_details_seq")
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "FIRST_NAME", length = 100)
    private String firstName;

    @Column(name = "LAST_NAME", length = 100)
    private String lastName;

    @Column(name = "PHONE_NUMBER", length = 13)
    private String phoneNumber;
}
