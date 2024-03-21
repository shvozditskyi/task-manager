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
import pl.kul.taskmanager.user.entity.requests.UserRequestMapper;
import pl.kul.taskmanager.user.entity.requests.UserRequestRepository;
import pl.kul.taskmanager.user.utils.UserUtils;

import java.util.List;

import static pl.kul.taskmanager.security.SecurityUtils.getUserId;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardUserRepository boardUserRepository;
    private final BoardMapper boardMapper;
    private final BoardUserMapper boardUserMapper;
    private final UserUtils userUtils;

    @Override
    @Transactional
    public void createBoard(BoardDTO boardDTO) {
        BoardEntity boardEntity = boardMapper.mapToEntity(boardDTO);
        boardRepository.save(boardEntity);
        UserDetailsEntity user = userUtils.findByUserId(getUserId());
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

    @Override
    public void setDefaultBoard(Long boardId) {
        boardUserRepository.findByUserIdAndBoardId(getUserId(), boardId)
                .ifPresentOrElse(this::makeBoardAsDefault,
                        () -> {
                            throw new RuntimeException("Board not found");
                        });
    }

    @Override
    public BoardUserDTO getDefaultBoardId() {
        return boardUserRepository.findByUserIdAndDefaultTrue(getUserId())
                .map(boardUserMapper::mapToDTO)
                .orElse(null);
    }

    @Override
    public void deleteBoard(Long boardId) {
        BoardUserEntity boardUserEntity = boardUserRepository.findByUserIdAndIsOwnerAndBoardId(getUserId(), boardId)
                .orElseThrow(() -> new RuntimeException("Board not found"));
        boardUserRepository.delete(boardUserEntity);
    }

    private void validateIfUserIsNotAlreadyInBoard(Long boardId, Long invitedUserId) {
        boardUserRepository.findByUserIdAndBoardId(invitedUserId, boardId)
                .ifPresent(bu -> {
                    throw new RuntimeException("User already has access to this board");
                });
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
        if (isDefault) {
            boardUserRepository.findByUserIdAndDefaultTrue(user.getId())
                    .ifPresent(bu -> bu.setIsDefault(false));
        }
    }

    private void makeBoardAsDefault(BoardUserEntity bu) {
        if (!bu.getBoard().getIsActive()) {
            throw new RuntimeException("Board is not active");
        }
        changeDefaultBoardForUser(true, bu.getUser());
        bu.setIsDefault(true);
        boardUserRepository.save(bu);
    }

    private BoardEntity findBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found"));
    }
}
