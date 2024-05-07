package pl.kul.taskmanager.board.mapper;

import ch.qos.logback.classic.spi.LoggingEventVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import pl.kul.taskmanager.board.dto.BoardDTO;
import pl.kul.taskmanager.board.dto.TaskStatusDTO;
import pl.kul.taskmanager.board.entity.BoardEntity;
import pl.kul.taskmanager.board.entity.BoardType;
import pl.kul.taskmanager.board.entity.BoardUserEntity;
import pl.kul.taskmanager.commons.AbstractMapper;
import pl.kul.taskmanager.security.SecurityUtils;
import pl.kul.taskmanager.task.dto.TaskDTO;
import pl.kul.taskmanager.user.UserDetailsDTO;
import pl.kul.taskmanager.user.entity.UserDetailsEntity;
import pl.kul.taskmanager.user.repository.UserDetailsRepository;
import pl.kul.taskmanager.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.kul.taskmanager.security.SecurityUtils.getUserId;

@Component
public class BoardMapper implements AbstractMapper<BoardDTO, BoardEntity> {

    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;

    public BoardMapper(UserRepository userRepository, UserDetailsRepository userDetailsRepository) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    public BoardEntity mapToEntity(BoardDTO boardDTO) {
        return BoardEntity.builder()
                .name(boardDTO.getName())
                .description(boardDTO.getDescription())
                .color(boardDTO.getColor() == null ? "#FFFFFF" : boardDTO.getColor())
                .creationDate(LocalDateTime.now())
                .isActive(boardDTO.getIsActive())
                .boardType(boardDTO.getBoardType() == null ? BoardType.PRIVATE : boardDTO.getBoardType())
                .participants(createNewBoardUserEntitySet())
                .build();
    }

    private Set<BoardUserEntity> createNewBoardUserEntitySet() {
        BoardUserEntity boardUserEntity = new BoardUserEntity();
        boardUserEntity.setIsOwner(true);
        boardUserEntity.setIsDefault(true);
        boardUserEntity.setUser(userDetailsRepository.getReferenceById(getUserId()));
        return Set.of(boardUserEntity);
    }

    public BoardDTO mapToDTO(BoardEntity boardEntity) {
        return BoardDTO.builder()
                .id(boardEntity.getId())
                .name(boardEntity.getName())
                .description(boardEntity.getDescription())
                .color(boardEntity.getColor())
                .statuses(getStatusesDTO(boardEntity))
                .boardType(boardEntity.getBoardType())
                .isActive(boardEntity.getIsActive())
                .creationDate(boardEntity.getCreationDate())
                .tasks(mapTasks(boardEntity))
                .participants(getParticipants(boardEntity))
                .numberOfUsers(boardEntity.getParticipants().size())
                .build();
    }

    private Set<UserDetailsDTO> getParticipants(BoardEntity boardEntity) {
        return boardEntity.getParticipants().stream().map(BoardUserEntity::getUser).map(this::mapToUserDetailsDTO).collect(Collectors.toSet());
    }

    private List<TaskStatusDTO> getStatusesDTO(BoardEntity boardEntity) {
        return boardEntity.getStatuses().stream()
                .map(status -> TaskStatusDTO.builder()
                        .id(status.getId())
                        .name(status.getName())
                        .orderNumber(status.getOrderNumber())
                        .build())
                .collect(Collectors.toList());
    }

    private Set<TaskDTO> mapTasks(BoardEntity boardEntity) {
        return boardEntity.getTasks().stream()
                .map(task -> TaskDTO.builder()
                        .id(task.getId())
                        .name(task.getName())
                        .description(task.getDescription())
                        .statusId(task.getStatus().getId())
                        .boardId(task.getBoard().getId())
                        .createdBy(mapToUserDetailsDTO(task.getCreatedBy()))
                        .build())
                .collect(Collectors.toSet());
    }

    private UserDetailsDTO mapToUserDetailsDTO(UserDetailsEntity createdBy) {
        return UserDetailsDTO.builder()
                .email(createdBy.getUser().getEmail())
                .firstName(createdBy.getFirstName())
                .lastName(createdBy.getLastName())
                .phoneNumber(createdBy.getPhoneNumber())
                .build();
    }
}
