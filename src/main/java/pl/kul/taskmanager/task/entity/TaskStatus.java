package pl.kul.taskmanager.task.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.YesNoConverter;
import pl.kul.taskmanager.board.entity.BoardEntity;
import pl.kul.taskmanager.commons.AbstractEntity;

import java.util.List;

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
    @Builder.Default
    private boolean active = Boolean.TRUE;

    @Column(name = "done_status")
    private boolean doneStatus;

    @Column(name = "order_number")
    private int orderNumber;

    @JoinColumn(name = "board_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = BoardEntity.class,
                 cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH })
    private BoardEntity board;

    @OneToOne(mappedBy = "status", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private TaskEntity tasks;
}
