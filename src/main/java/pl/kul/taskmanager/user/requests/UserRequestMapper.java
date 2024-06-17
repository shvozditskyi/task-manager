package pl.kul.taskmanager.user.requests;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kul.taskmanager.board.repository.BoardRepository;
import pl.kul.taskmanager.board.utils.BoardUtils;
import pl.kul.taskmanager.commons.AbstractMapper;
import pl.kul.taskmanager.user.entity.UserDetailsEntity;
import pl.kul.taskmanager.user.entity.UserEntity;
import pl.kul.taskmanager.user.requests.enums.UserRequestStatus;
import pl.kul.taskmanager.user.requests.enums.UserRequestType;
import pl.kul.taskmanager.user.utils.UserUtils;

import java.util.Optional;

import static pl.kul.taskmanager.security.SecurityUtils.getUserId;

@Component
@RequiredArgsConstructor
public class UserRequestMapper implements AbstractMapper<UserRequestDTO, UserRequestEntity>{

    private final UserUtils userUtils;
    private final BoardRepository boardRepository;

    @Override
    public UserRequestEntity mapToEntity(UserRequestDTO dto) {
        return UserRequestEntity.builder()
                .board(boardRepository.getReferenceById(dto.getBoardId()))
                .requestMessage(dto.getRequestMessage())
                .requestType(UserRequestType.BOARD)
                .requestStatus(UserRequestStatus.PENDING)
                .sender(userUtils.getLoggedUserDetails())
                .receiver(userUtils.findByEmail(dto.getReceiverEmail()))
                .build();
    }

    @Override
    public UserRequestDTO mapToDTO(UserRequestEntity entity) {
        return UserRequestDTO.builder()
                .id(entity.getId())
                .requestMessage(entity.getRequestMessage())
                .requestStatus(entity.getRequestStatus())
                .senderEmail(getNullSafeEmail(entity.getSender()))
                .receiverEmail(getNullSafeEmail(entity.getReceiver()))
                .boardId(entity.getBoard().getId())
                .build();
    }

    private String getNullSafeEmail(UserDetailsEntity userDetailsEntity) {
        return Optional.of(userDetailsEntity)
                .map(UserDetailsEntity::getUser)
                .map(UserEntity::getEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
