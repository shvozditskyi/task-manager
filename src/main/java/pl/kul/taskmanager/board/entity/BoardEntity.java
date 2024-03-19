package pl.kul.taskmanager.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.type.YesNoConverter;

import java.time.LocalDateTime;

@Entity
@Table(name = "BOARD")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardEntity {
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

    //todo: add relation to groups after groups implementation
}
