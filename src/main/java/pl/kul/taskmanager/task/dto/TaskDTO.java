package pl.kul.taskmanager.task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kul.taskmanager.commons.AbstractDTO;
import pl.kul.taskmanager.user.UserDetailsDTO;

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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDetailsDTO createdBy;
}
