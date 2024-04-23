package pl.kul.taskmanager.board.mapper;

import org.springframework.stereotype.Component;
import pl.kul.taskmanager.board.dto.BoardDTO;
import pl.kul.taskmanager.board.entity.BoardEntity;
import pl.kul.taskmanager.board.entity.BoardType;
import pl.kul.taskmanager.commons.AbstractMapper;
import pl.kul.taskmanager.task.dto.TaskDTO;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BoardMapper implements AbstractMapper<BoardDTO, BoardEntity> {

    public BoardEntity mapToEntity(BoardDTO boardDTO) {
        return BoardEntity.builder()
                .name(boardDTO.getName())
                .description(boardDTO.getDescription())
                .color(boardDTO.getColor() == null ? "#FFFFFF" : boardDTO.getColor())
                .creationDate(LocalDateTime.now())
                .isActive(boardDTO.getIsActive())
                .boardType(boardDTO.getBoardType() == null ? BoardType.PRIVATE : boardDTO.getBoardType())
                .build();
    }

    public BoardDTO mapToDTO(BoardEntity boardEntity) {
        return BoardDTO.builder()
                .name(boardEntity.getName())
                .description(boardEntity.getDescription())
                .color(boardEntity.getColor())
                .boardType(boardEntity.getBoardType())
                .isActive(boardEntity.getIsActive())
                .creationDate(boardEntity.getCreationDate())
                .tasks(mapTasks(boardEntity))
                .build();
    }

    private Set<TaskDTO> mapTasks(BoardEntity boardEntity) {
        return boardEntity.getTasks().stream()
                .map(task -> TaskDTO.builder()
                        .name(task.getName())
                        .description(task.getDescription())
                        .statusId(task.getStatus().getId())
                        .boardId(task.getBoard().getId())
                        .build())
                .collect(Collectors.toSet());
    }
}
