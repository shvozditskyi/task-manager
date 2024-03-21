package pl.kul.taskmanager.board.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.YesNoConverter;
import pl.kul.taskmanager.commons.AbstractEntity;
import pl.kul.taskmanager.user.entity.UserDetailsEntity;
import pl.kul.taskmanager.user.entity.UserEntity;

@Entity
@Table(name = "BOARD_USER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardUserEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_user_seq")
    private Long id;

    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "BOARD_ID", referencedColumnName = "ID")
    private BoardEntity board;

    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private UserDetailsEntity user;

    @Column(name = "IS_OWNER", nullable = false)
    @Convert(converter = YesNoConverter.class)
    private Boolean isOwner;

    @Column(name = "IS_DEFAULT", nullable = false)
    @Convert(converter = YesNoConverter.class)
    private Boolean isDefault;
}
