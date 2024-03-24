package pl.kul.taskmanager.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kul.taskmanager.board.dto.BoardDTO;
import pl.kul.taskmanager.board.dto.BoardUserDTO;
import pl.kul.taskmanager.board.service.BoardService;
import pl.kul.taskmanager.user.requests.UserRequestDTO;
import pl.kul.taskmanager.user.requests.UserRequestService;

import java.util.List;

import static pl.kul.taskmanager.commons.RestConstants.BOARDS;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BOARDS)
public class BoardController {
    private final BoardService boardService;
    private final UserRequestService userRequestService;

    @PostMapping
    public ResponseEntity<Void> createPrivateBoard(@RequestBody @Valid BoardDTO boardDTO) {
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

    @PostMapping("default/{boardId}")
    public ResponseEntity<Void> makeBoardAsDefault(@PathVariable(name = "boardId") Long boardId) {
        log.debug("Making board as default for single user");
        boardService.setDefaultBoard(boardId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/default")
    public ResponseEntity<BoardUserDTO> getDefaultBoardId() {
        log.debug("Getting default board for single user");
        return ResponseEntity.ok(boardService.getDefaultBoardId());
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable(name = "boardId") Long boardId) {
        log.debug("Deleting board for single user");
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/invite")
    public ResponseEntity<Void> inviteUserToBoard(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        log.debug("Inviting user to board");
        userRequestService.inviteUserToBoard(userRequestDTO);
        return ResponseEntity.ok().build();
    }
}
