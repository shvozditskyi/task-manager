package pl.kul.taskmanager.board.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.YesNoConverter;
import pl.kul.taskmanager.commons.AbstractEntity;
import pl.kul.taskmanager.task.entity.TaskEntity;
import pl.kul.taskmanager.user.entity.UserDetailsEntity;

@Entity
@Table(name = "BOARD_TASK")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardTaskEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_task_seq")
    private Long id;

    @OneToOne(cascade = { CascadeType.ALL }, orphanRemoval = true)
    @JoinColumn(name = "BOARD_ID", referencedColumnName = "ID")
    private BoardEntity board;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "TASK_ID", referencedColumnName = "ID")
    private TaskEntity task;
}
