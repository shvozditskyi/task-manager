package pl.kul.taskmanager.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kul.taskmanager.board.BoardDTO;
import pl.kul.taskmanager.board.service.BoardService;

import static pl.kul.taskmanager.commons.RestConstants.BOARD;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BOARD)
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Void> createBoardForSingleUser(@Valid BoardDTO boardDTO) {
        log.debug("Creating board: {}", boardDTO);
        boardService.createBoard(boardDTO);
        return ResponseEntity.ok().build();
    }
}
