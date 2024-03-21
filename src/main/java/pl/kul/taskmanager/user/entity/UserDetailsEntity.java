package pl.kul.taskmanager.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kul.taskmanager.board.entity.BoardUserEntity;
import pl.kul.taskmanager.commons.AbstractEntity;
import pl.kul.taskmanager.user.entity.requests.UserRequestEntity;

import java.util.Set;

@Entity
@Table(name = "USER_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsEntity extends AbstractEntity {
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

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<BoardUserEntity> userBoards;

    @JsonIgnore
    @OneToMany(mappedBy = "sender", cascade = { CascadeType.ALL })
    private Set<UserRequestEntity> joinRequests;

    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private UserEntity user;
}
