package pl.kul.taskmanager.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kul.taskmanager.board.repository.BoardRepository;
import pl.kul.taskmanager.task.dto.ShortTaskDTO;
import pl.kul.taskmanager.task.dto.TaskDTO;
import pl.kul.taskmanager.task.entity.TaskEntity;
import pl.kul.taskmanager.task.entity.TaskStatus;
import pl.kul.taskmanager.task.mapper.TaskMapper;
import pl.kul.taskmanager.task.repository.TaskRepository;
import pl.kul.taskmanager.task.repository.TaskStatusRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final BoardRepository boardRepository;
    private final TaskMapper taskMapper;

    @Override
    public void createTask(TaskDTO taskDTO) {
        // todo add validation user->board
        log.debug("Creating task: {}", taskDTO);
        TaskEntity taskEntity = taskMapper.mapToEntity(taskDTO);
        taskRepository.save(taskEntity);
        log.info("Task created: {}", taskEntity.getId());
    }

    @Override
    public void deleteTask(Long taskId) {
        // todo add validation user->board
        log.debug("Deleting task with id {}", taskId);
        taskRepository.deleteById(taskId);
        log.debug("Task with id {} deleted successfully", taskId);
    }

    @Override
    public void editTask(Long taskId, ShortTaskDTO taskDTO) {
        // todo add validation user->board
        log.debug("Editing task with id {}", taskId);
        TaskEntity task = taskRepository.getReferenceById(taskId);
        if (taskDTO.getName() != null) {
            task.setName(taskDTO.getName());
        }
        if (taskDTO.getDescription() != null) {
            task.setDescription(taskDTO.getDescription());
        }
        taskRepository.save(task);
        log.debug("Task with id {} edited successfully", taskId);
    }

    @Override
    public void changeTaskStatus(Long taskId, Long statusId) {
        TaskStatus newStatus = taskStatusRepository.getReferenceById(statusId);
        log.debug("Changing status of task with id {} to {} ", taskId, newStatus.getName());
        TaskEntity task = taskRepository.getReferenceById(taskId);
        task.setStatus(newStatus);
        taskRepository.save(task);
        log.debug("Status changed successfully to {}", newStatus.getName());
    }
}
