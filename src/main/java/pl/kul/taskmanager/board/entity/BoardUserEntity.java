package pl.kul.taskmanager.board.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.YesNoConverter;
import pl.kul.taskmanager.user.entity.UserEntity;

@Entity
@Table(name = "BOARD_USER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_user_seq")
    private Long id;

    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "OWNER_ID", referencedColumnName = "ID")
    private UserEntity user;

    @Column(name = "IS_OWNER", nullable = false)
    @Convert(converter = YesNoConverter.class)
    private Boolean isOwner;
}
