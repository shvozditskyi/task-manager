package pl.kul.taskmanager.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kul.taskmanager.task.entity.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
