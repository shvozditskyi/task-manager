package pl.kul.taskmanager.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kul.taskmanager.task.entity.TaskStatus;

import java.util.Optional;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {
    int countAllByBoardId(long boardId);

    @Query("""
        select ts
        from TaskStatus ts
        join ts.board b
        where ts.id = :l and b.id = :boardId
""")
    Optional<TaskStatus> findByStatusIdAndBoardId(long l, Long boardId);
}
