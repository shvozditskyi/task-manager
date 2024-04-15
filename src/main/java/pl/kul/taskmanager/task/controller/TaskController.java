package pl.kul.taskmanager.task.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kul.taskmanager.task.dto.ShortTaskDTO;
import pl.kul.taskmanager.task.dto.TaskDTO;
import pl.kul.taskmanager.task.service.TaskService;

import static pl.kul.taskmanager.commons.RestConstants.TASKS;

@RestController
@RequestMapping(TASKS)
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Void> createTask(@RequestBody TaskDTO taskDTO) {
        taskService.createTask(taskDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Void> editTask(@PathVariable Long taskId,
                                         @RequestBody ShortTaskDTO taskDTO) {
        taskService.editTask(taskId, taskDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{taskId}/{statusId}")
    public ResponseEntity<Void> changeTaskStatus(@PathVariable Long taskId,
                                                 @PathVariable Long statusId) {
        taskService.changeTaskStatus(taskId, statusId);
        return ResponseEntity.ok().build();
    }
}
