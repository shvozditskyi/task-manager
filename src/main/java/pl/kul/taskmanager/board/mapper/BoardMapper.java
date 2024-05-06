package pl.kul.taskmanager.board.mapper;

import org.springframework.stereotype.Component;
import pl.kul.taskmanager.board.dto.BoardDTO;
import pl.kul.taskmanager.board.dto.TaskStatusDTO;
import pl.kul.taskmanager.board.entity.BoardEntity;
import pl.kul.taskmanager.board.entity.BoardType;
import pl.kul.taskmanager.commons.AbstractMapper;
import pl.kul.taskmanager.task.dto.TaskDTO;

import java.time.LocalDateTime;
import java.util.List;
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
                .id(boardEntity.getId())
                .name(boardEntity.getName())
                .description(boardEntity.getDescription())
                .color(boardEntity.getColor())
                .statuses(getStatusesDTO(boardEntity))
                .boardType(boardEntity.getBoardType())
                .isActive(boardEntity.getIsActive())
                .creationDate(boardEntity.getCreationDate())
                .tasks(mapTasks(boardEntity))
                .build();
    }

    private List<TaskStatusDTO> getStatusesDTO(BoardEntity boardEntity) {
        return boardEntity.getStatuses().stream()
                .map(status -> TaskStatusDTO.builder()
                        .id(status.getId())
                        .name(status.getName())
                        .orderNumber(status.getOrderNumber())
                        .build())
                .collect(Collectors.toList());
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
