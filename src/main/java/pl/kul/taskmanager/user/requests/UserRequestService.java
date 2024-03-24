package pl.kul.taskmanager.user.requests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kul.taskmanager.board.repository.BoardUserRepository;
import pl.kul.taskmanager.board.service.BoardService;
import pl.kul.taskmanager.user.repository.UserDetailsRepository;
import pl.kul.taskmanager.user.requests.enums.UserRequestStatus;
import pl.kul.taskmanager.user.utils.UserUtils;

import java.util.List;
import java.util.Optional;

import static pl.kul.taskmanager.security.SecurityUtils.getUserId;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRequestService {

    private final UserRequestRepository userRequestRepository;
    private final BoardUserRepository boardUserRepository;
    private final UserRequestMapper userRequestMapper;
    private final BoardService boardService;

    private final UserUtils userUtils;

    public void inviteUserToBoard(UserRequestDTO userRequestDTO) {
        validateUserRequest(userRequestDTO);
        UserRequestEntity userRequestEntity = userRequestMapper.mapToEntity(userRequestDTO);
        userRequestRepository.save(userRequestEntity);
    }

    public void deleteUserRequest(Long requestId) {
        Long result = userRequestRepository.deleteByIdAndSenderReceiverId(requestId, getUserId());
        if (result == 0L) {
            throw new RuntimeException("Request not found");
        }
    }

    public void manageUserRequest(Long requestId, Boolean accepted) {
        Optional.ofNullable(accepted)
                .map(value -> accepted ? UserRequestStatus.ACCEPTED : UserRequestStatus.REJECTED)
                .ifPresent(status -> changeUserRequestStatus(requestId, status));
    }

    private void changeUserRequestStatus(Long requestId, UserRequestStatus status) {
        UserRequestEntity userRequestEntity = userRequestRepository.findByIdAndReceiverId(requestId, getUserId())
                .orElseThrow(() -> new RuntimeException("Request not found"));
        userRequestEntity.setRequestStatus(status);
        if (status.equals(UserRequestStatus.ACCEPTED)) {
            addUserToBoard(userRequestEntity);
        }
        userRequestEntity.setIsActive(false);
        userRequestRepository.save(userRequestEntity);
    }

    private void addUserToBoard(UserRequestEntity userRequestEntity) {
        Long receiverId = userRequestEntity.getReceiver().getUser().getId();
        Long boardId = userRequestEntity.getBoard().getId();
        boardService.addUserToBoard(receiverId, boardId);
    }

    private void validateUserRequest(UserRequestDTO userRequestDTO) {
        isUserOwnerOfBoard(userRequestDTO.getBoardId());
        isExistRequestByReceiverEmail(userRequestDTO);
        Long receiverId = userUtils.getIdByEmail(userRequestDTO.getReceiverEmail());
        validateIfUserIsNotAlreadyInBoard(userRequestDTO.getBoardId(), receiverId);
    }

    private void isUserOwnerOfBoard(Long boardId) {
        boardUserRepository.findByUserIdAndIsOwnerAndBoardId(getUserId(), boardId)
                .orElseThrow(() -> new RuntimeException("You are not the owner of this board"));
    }

    private void isExistRequestByReceiverEmail(UserRequestDTO userRequestDTO) {
        userRequestRepository.findByReceiverEmailAndIsActive(userRequestDTO.getSenderEmail()).ifPresent(
                request -> {
                    throw new RuntimeException("Request already exists");
                }
        );
    }

    private void validateIfUserIsNotAlreadyInBoard(Long boardId, Long invitedUserId) {
        boardUserRepository.findByUserIdAndBoardId(invitedUserId, boardId)
                .ifPresent(bu -> {
                    throw new RuntimeException("User already has access to this board");
                });
    }

    public List<UserRequestDTO> getUserRequests() {
        return userRequestRepository.findAllByReceiverId(getUserId())
                .stream().map(userRequestMapper::mapToDTO).toList();
    }
}
