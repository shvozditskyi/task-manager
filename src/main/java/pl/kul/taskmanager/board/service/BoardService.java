package pl.kul.taskmanager.board.service;

import pl.kul.taskmanager.board.dto.BoardDTO;
import pl.kul.taskmanager.board.dto.BoardUserDTO;
import pl.kul.taskmanager.board.dto.TaskStatusDTO;

import java.util.List;

public interface BoardService {
    void createBoard(BoardDTO boardDTO);

    BoardDTO getBoard(Long boardId);

    List<BoardUserDTO> getUserBoards();

    void setDefaultBoard(Long boardId);

    BoardDTO getDefaultBoard();

    void deleteBoard(Long boardId);

    void addUserToBoard(Long receiverId, Long boardId);

    void createTaskStatus(Long boardId, String name);

    void changeBoardName(Long boardId, String name);
}
