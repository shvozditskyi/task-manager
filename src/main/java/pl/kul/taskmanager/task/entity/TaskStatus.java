package pl.kul.taskmanager.task.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.YesNoConverter;
import pl.kul.taskmanager.board.entity.BoardEntity;
import pl.kul.taskmanager.commons.AbstractEntity;

@Entity
@Table(name = "TASK_STATUS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskStatus extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_status_seq")
    private Long id;

    private String name;

    @Column(name = "active")
    @Convert(converter = YesNoConverter.class)
    private boolean active;

    @JoinColumn(name = "board_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = BoardEntity.class)
    private BoardEntity board;
}