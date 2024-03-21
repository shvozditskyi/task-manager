package pl.kul.taskmanager.user.entity.requests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRequestService {

    private final UserRequestRepository userRequestRepository;
    private final UserRequestMapper userRequestMapper;

    public void inviteUserToBoard(UserRequestDTO userRequestDTO) {
        UserRequestEntity userRequestEntity = userRequestMapper.mapToEntity(userRequestDTO);
        userRequestRepository.save(userRequestEntity);
    }
}
