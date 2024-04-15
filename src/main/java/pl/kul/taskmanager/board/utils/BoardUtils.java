package pl.kul.taskmanager.board.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kul.taskmanager.board.entity.BoardEntity;
import pl.kul.taskmanager.board.mapper.BoardMapper;
import pl.kul.taskmanager.board.service.BoardService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BoardUtils {

    private final BoardService boardService;
    private final BoardMapper boardMapper;

    public BoardEntity getBoardEntityById(Long boardId) {
        return Optional.ofNullable(boardId)
                .map(boardService::getBoard)
                .map(boardMapper::mapToEntity)
                .orElseThrow(() -> new RuntimeException("Board not found"));
    }
}
