package pl.kul.taskmanager.board.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import pl.kul.taskmanager.board.entity.BoardType;
import pl.kul.taskmanager.commons.AbstractDTO;
import pl.kul.taskmanager.commons.ValidationMessages;
import pl.kul.taskmanager.user.UserDetailsDTO;

import java.time.LocalDateTime;
import java.util.List;

import static pl.kul.taskmanager.commons.ValidationMessages.NAME_IS_REQUIRED;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO extends AbstractDTO {
    @NotNull(message = NAME_IS_REQUIRED)
    @Size(max = 50, message = ValidationMessages.NAME_LENGTH)
    private String name;
    @Size(max = 200, message = ValidationMessages.DESCRIPTION_LENGTH)
    private String description;
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = ValidationMessages.COLOR_IS_NOT_VALID)
    private String color;
    private BoardType boardType;
    private LocalDateTime creationDate;
    @Builder.Default
    private Boolean isActive = Boolean.TRUE;
    @NotNull
    @Builder.Default
    private Boolean isDefault = Boolean.FALSE;
    private Long numberOfTasks;
    private Long numberOfUsers;
}
