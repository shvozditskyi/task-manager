package pl.kul.taskmanager.task.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kul.taskmanager.commons.AbstractDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO extends AbstractDTO {
    private Long id;
    @NotNull
    private String name;
    private String description;
    @NotNull
    private Long boardId;
    @NotNull
    private Long statusId;
    // READONLY
    private Long createdBy;
}
