package pl.kul.taskmanager.task.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
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

    @Override
    public TaskEntity mapToEntity(TaskDTO dto) {
        return TaskEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .status(taskStatusRepository.getReferenceById(dto.getStatusId()))
                .createdBy(userDetailsRepository.getReferenceById(SecurityUtils.getUserId()))
                .build();
    }

    @Override
    public TaskDTO mapToDTO(TaskEntity dto) {
        return null;
    }
}
