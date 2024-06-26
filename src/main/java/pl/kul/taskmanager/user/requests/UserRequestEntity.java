package pl.kul.taskmanager.user.requests;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.YesNoConverter;
import pl.kul.taskmanager.board.entity.BoardEntity;
import pl.kul.taskmanager.commons.AbstractEntity;
import pl.kul.taskmanager.user.entity.UserDetailsEntity;
import pl.kul.taskmanager.user.requests.enums.UserRequestStatus;
import pl.kul.taskmanager.user.requests.enums.UserRequestType;

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

    @Column(name = "IS_ACTIVE", nullable = false)
    @Convert(converter = YesNoConverter.class)
    @Builder.Default
    private Boolean isActive = Boolean.TRUE;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH },
            fetch = FetchType.LAZY)
    @JoinColumn(name = "SENDER_ID", referencedColumnName = "ID")
    private UserDetailsEntity sender;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH },
            fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_ID", referencedColumnName = "ID")
    private UserDetailsEntity receiver;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "BOARD_ID", referencedColumnName = "ID")
    private BoardEntity board;
}
