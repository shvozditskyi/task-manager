package pl.kul.taskmanager.task.service;

import pl.kul.taskmanager.task.dto.ShortTaskDTO;
import pl.kul.taskmanager.task.dto.TaskDTO;

public interface TaskService {
    void createTask(TaskDTO taskDTO);
    void deleteTask(Long taskId);
    void editTask(Long taskId, ShortTaskDTO taskDTO);
    void changeTaskStatus(Long taskId, Long statusId);

    void changeTaskStatusName(Long statusId, String name);
}
