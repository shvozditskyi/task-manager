package pl.kul.taskmanager.task.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kul.taskmanager.board.repository.BoardRepository;
import pl.kul.taskmanager.commons.AbstractMapper;
import pl.kul.taskmanager.security.SecurityUtils;
import pl.kul.taskmanager.task.dto.TaskDTO;
import pl.kul.taskmanager.task.entity.TaskEntity;
import pl.kul.taskmanager.task.repository.TaskStatusRepository;
import pl.kul.taskmanager.user.repository.UserDetailsRepository;

@Component
@RequiredArgsConstructor
public class TaskMapper implements AbstractMapper<TaskDTO, TaskEntity> {

    private final TaskStatusRepository taskStatusRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final BoardRepository boardRepository;

    @Override
    public TaskEntity mapToEntity(TaskDTO dto) {
        return TaskEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .status(taskStatusRepository.findByOrderNumberAndBoardId(0L, dto.getBoardId()).orElse(null))
                .createdBy(userDetailsRepository.getReferenceById(SecurityUtils.getUserId()))
                .board(boardRepository.getReferenceById(dto.getBoardId()))
                .build();
    }

    @Override
    public TaskDTO mapToDTO(TaskEntity dto) {
        return null;
    }
}
