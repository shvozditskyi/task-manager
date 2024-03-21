package pl.kul.taskmanager.user.entity.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonKey;
import jakarta.persistence.*;
import lombok.*;
import pl.kul.taskmanager.board.entity.BoardEntity;
import pl.kul.taskmanager.commons.AbstractEntity;
import pl.kul.taskmanager.user.entity.UserDetailsEntity;
import pl.kul.taskmanager.user.entity.requests.enums.UserRequestStatus;
import pl.kul.taskmanager.user.entity.requests.enums.UserRequestType;

@Entity
@Table(name = "INVITATION_REQUEST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invitation_request_seq")
    private Long id;

    @Column(name = "MESSAGE", length = 500)
    private String requestMessage;

    @Column(name = "STATUS", length = 10)
    @Enumerated(EnumType.STRING)
    private UserRequestStatus requestStatus;

    @Column(name = "TYPE", length = 5)
    @Enumerated(EnumType.STRING)
    private UserRequestType requestType;

    @ManyToOne
    @JoinColumn(name = "SENDER_ID", referencedColumnName = "ID")
    private UserDetailsEntity sender;

    @ManyToOne
    @JoinColumn(name = "RECEIVER_ID", referencedColumnName = "ID")
    private UserDetailsEntity receiver;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID", referencedColumnName = "ID")
    private BoardEntity board;
}
