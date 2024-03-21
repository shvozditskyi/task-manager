package pl.kul.taskmanager.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kul.taskmanager.board.dto.BoardDTO;
import pl.kul.taskmanager.board.dto.BoardUserDTO;
import pl.kul.taskmanager.board.service.BoardService;

import java.util.List;

import static pl.kul.taskmanager.commons.RestConstants.BOARD;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BOARD)
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Void> createPrivateBoard(@Valid BoardDTO boardDTO) {
        log.debug("Creating board: {}", boardDTO);
        boardService.createBoard(boardDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<BoardUserDTO>> getUserBoards() {
        log.debug("Getting board for single user");
        return ResponseEntity.ok(boardService.getUserBoards());
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDTO> getBoardById(@PathVariable(name = "boardId") Long boardId) {
        log.debug("Getting board for single user");
        return ResponseEntity.ok(boardService.getBoard(boardId));
    }
}
