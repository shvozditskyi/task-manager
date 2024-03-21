package pl.kul.taskmanager.board.service;

import pl.kul.taskmanager.board.dto.BoardDTO;
import pl.kul.taskmanager.board.dto.BoardUserDTO;

import java.util.List;

public interface BoardService {
    void createBoard(BoardDTO boardDTO);

    BoardDTO getBoard(Long boardId);

    List<BoardUserDTO> getUserBoards();
}
