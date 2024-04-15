package pl.kul.taskmanager.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kul.taskmanager.board.entity.BoardEntity;
import pl.kul.taskmanager.commons.AbstractEntity;
import pl.kul.taskmanager.user.entity.UserDetailsEntity;

@Entity
@Table(name = "TASK")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description", length = 200)
    private String description;

    @JoinColumn(name = "status_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            targetEntity = TaskStatus.class)
    private TaskStatus status;

    @JoinColumn(name = "created_by")
    @ManyToOne(fetch = FetchType.LAZY, optional = false,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            targetEntity = UserDetailsEntity.class)
    private UserDetailsEntity createdBy;

    @JoinColumn(name = "board_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            targetEntity = BoardEntity.class)
    private BoardEntity board;
}
