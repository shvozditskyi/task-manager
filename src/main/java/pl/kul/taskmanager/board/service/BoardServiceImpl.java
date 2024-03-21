package pl.kul.taskmanager.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kul.taskmanager.board.dto.BoardDTO;
import pl.kul.taskmanager.board.dto.BoardUserDTO;
import pl.kul.taskmanager.board.entity.BoardEntity;
import pl.kul.taskmanager.board.entity.BoardUserEntity;
import pl.kul.taskmanager.board.mapper.BoardMapper;
import pl.kul.taskmanager.board.mapper.BoardUserMapper;
import pl.kul.taskmanager.board.repository.BoardRepository;
import pl.kul.taskmanager.board.repository.BoardUserRepository;
import pl.kul.taskmanager.user.entity.UserDetailsEntity;
import pl.kul.taskmanager.user.repository.UserDetailsRepository;

import java.util.List;

import static pl.kul.taskmanager.security.SecurityUtils.getUserId;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardUserRepository boardUserRepository;
    private final UserDetailsRepository userDetailRepository;
    private final BoardMapper boardMapper;
    private final BoardUserMapper boardUserMapper;

    @Override
    @Transactional
    public void createBoard(BoardDTO boardDTO) {
        BoardEntity boardEntity = boardMapper.mapToEntity(boardDTO);
        boardRepository.save(boardEntity);
        UserDetailsEntity user = findUserById();
        saveBoardForUser(boardEntity, user, boardDTO.getIsDefault());
    }

    @Override
    public List<BoardUserDTO> getUserBoards() {
        return boardUserRepository.findActiveBoardsByUserId(getUserId())
                .stream()
                .map(boardUserMapper::mapToDTO)
                .toList();
    }

    @Override
    public BoardDTO getBoard(Long boardId) {
        return boardRepository.findActiveBoardByBoardIdAndUserId(boardId, getUserId())
                .map(boardMapper::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Board not found"));
    }

    private void saveBoardForUser(BoardEntity boardEntity, UserDetailsEntity user, Boolean isDefault) {
        changeDefaultBoardForUser(isDefault, user);
        BoardUserEntity boardUserEntity = BoardUserEntity.builder()
                .board(boardEntity)
                .user(user)
                .isOwner(true)
                .isDefault(isDefault)
                .build();
        boardUserRepository.save(boardUserEntity);
    }

    private void changeDefaultBoardForUser(Boolean isDefault, UserDetailsEntity user) {
        if(isDefault) {
            boardUserRepository.findByUserIdAndDefaultTrue(user.getId())
                    .ifPresent(bu -> bu.setIsDefault(false));
        }
    }

    private UserDetailsEntity findUserById() {
        return userDetailRepository.findById(getUserId()).orElseThrow(
                () -> new RuntimeException("User not found"));
    }
}
