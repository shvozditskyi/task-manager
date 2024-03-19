package pl.kul.taskmanager.board.mapper;

import org.springframework.stereotype.Component;
import pl.kul.taskmanager.board.BoardDTO;
import pl.kul.taskmanager.board.entity.BoardEntity;
import pl.kul.taskmanager.board.entity.BoardType;

import java.time.LocalDateTime;

@Component
public class BoardMapper {

    public static BoardEntity mapToEntity(BoardDTO boardDTO) {
        return BoardEntity.builder()
                .name(boardDTO.getName())
                .description(boardDTO.getDescription())
                .color(boardDTO.getColor() == null ? "#FFFFFF" : boardDTO.getColor())
                .creationDate(LocalDateTime.now())
                .isActive(true)
                .boardType(boardDTO.getBoardType() == null ? BoardType.PUBLIC : boardDTO.getBoardType())
                .build();
    }

    public static BoardDTO mapToDTO(BoardEntity boardEntity) {
        return BoardDTO.builder()
                .name(boardEntity.getName())
                .description(boardEntity.getDescription())
                .color(boardEntity.getColor())
                .boardType(boardEntity.getBoardType())
                .isActive(boardEntity.getIsActive())
                .creationDate(boardEntity.getCreationDate())
                .build();
    }
}
