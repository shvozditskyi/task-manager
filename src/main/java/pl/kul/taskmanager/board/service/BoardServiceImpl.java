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
import pl.kul.taskmanager.board.dto.TaskStatusDTO;
import pl.kul.taskmanager.task.entity.TaskStatus;
import pl.kul.taskmanager.task.repository.TaskStatusRepository;
import pl.kul.taskmanager.user.entity.UserDetailsEntity;
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
    private final TaskStatusRepository taskStatusRepository;

    @Override
    @Transactional
    public void createBoard(BoardDTO boardDTO) {
        BoardEntity boardEntity = boardMapper.mapToEntity(boardDTO);
        boardRepository.save(boardEntity);
        UserDetailsEntity user = userUtils.findByUserId(getUserId());
        createDefaultTaskStatues(boardEntity);
        saveBoardForUser(boardEntity, user, boardDTO.getIsDefault(), true);
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

    @Override
    public void addUserToBoard(Long receiverId, Long boardId) {
        UserDetailsEntity user = userUtils.findByUserId(receiverId);
        BoardEntity boardEntity = findBoardById(boardId);
        saveBoardForUser(boardEntity, user, false, false);
    }

    @Override
    public void createTaskStatus(TaskStatusDTO taskStatusDTO) {
        log.debug("Creating new status: {}", taskStatusDTO);
        TaskStatus taskStatus = TaskStatus.builder()
                .name(taskStatusDTO.getName())
                .board(boardRepository.getReferenceById(taskStatusDTO.getBoardId()))
                .orderNumber(taskStatusRepository.countAllByBoardId(taskStatusDTO.getBoardId() + 1))
                .build();
        taskStatusRepository.save(taskStatus);
        log.info("Status created: {}", taskStatus.getId());
    }

    private void saveBoardForUser(BoardEntity boardEntity, UserDetailsEntity user, Boolean isDefault, Boolean isOwner) {
        changeDefaultBoardForUser(isDefault, user);
        BoardUserEntity boardUserEntity = BoardUserEntity.builder()
                .board(boardEntity)
                .user(user)
                .isOwner(isOwner)
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

    private void createDefaultTaskStatues(BoardEntity boardEntity) {
        List<TaskStatus> statuses = List.of(
                buildTaskStatus("To Do", boardEntity,0),
                buildTaskStatus("In Progress", boardEntity, 1),
                buildTaskStatus("Done", boardEntity, 2)
        );
        taskStatusRepository.saveAll(statuses);
    }

    private TaskStatus buildTaskStatus(String name, BoardEntity boardEntity, int orderNumber){
        return TaskStatus.builder()
                .name(name)
                .orderNumber(orderNumber)
                .board(boardEntity)
                .build();
    }
}
