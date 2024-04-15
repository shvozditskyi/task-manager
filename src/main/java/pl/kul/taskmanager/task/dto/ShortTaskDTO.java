package pl.kul.taskmanager.task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kul.taskmanager.commons.AbstractDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShortTaskDTO extends AbstractDTO {
    private String name;
    private String description;
}
