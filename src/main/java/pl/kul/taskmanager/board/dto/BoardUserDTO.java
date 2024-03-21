package pl.kul.taskmanager.board.dto;

import lombok.*;
import pl.kul.taskmanager.commons.AbstractDTO;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardUserDTO extends AbstractDTO {
    private BoardDTO board;
    private Long userId;
    private Boolean isOwner;
    private Boolean isDefault;
}
