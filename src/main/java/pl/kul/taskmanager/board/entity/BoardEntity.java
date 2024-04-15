package pl.kul.taskmanager.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.type.YesNoConverter;
import pl.kul.taskmanager.commons.AbstractEntity;
import pl.kul.taskmanager.task.entity.TaskStatus;
import pl.kul.taskmanager.user.requests.UserRequestEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "BOARD")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq")
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "color", length = 7, nullable = false)
    private String color;

    @Column(name = "board_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Column(name = "is_active", nullable = false)
    @Convert(converter = YesNoConverter.class)
    private Boolean isActive;

    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate;

    @JoinColumn(name = "status_id")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = TaskStatus.class)
    private List<TaskStatus> statuses;

    @JsonIgnore
    @OneToMany(mappedBy = "board", cascade = { CascadeType.ALL })
    private Set<UserRequestEntity> joinRequests;
}
