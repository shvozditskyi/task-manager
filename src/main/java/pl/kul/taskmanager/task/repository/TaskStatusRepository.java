package pl.kul.taskmanager.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kul.taskmanager.task.entity.TaskStatus;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {
    int countAllByBoardId(long boardId);
}
