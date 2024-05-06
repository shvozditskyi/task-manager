package pl.kul.taskmanager.board.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kul.taskmanager.commons.ValidationMessages;

import static pl.kul.taskmanager.commons.ValidationMessages.NAME_IS_REQUIRED;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskStatusDTO {
    private Long id;
    @NotNull(message = NAME_IS_REQUIRED)
    private String name;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int orderNumber;
}
