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

    BoardUserDTO getDefaultBoardId();

    void deleteBoard(Long boardId);

    void addUserToBoard(Long receiverId, Long boardId);

    void createTaskStatus(TaskStatusDTO taskStatusDTO);
}
