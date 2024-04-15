package pl.kul.taskmanager.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kul.taskmanager.board.entity.BoardTaskEntity;
import pl.kul.taskmanager.board.entity.BoardUserEntity;

import java.util.List;
import java.util.Optional;

public interface BoardTaskRepository extends JpaRepository<BoardTaskEntity, Long> {
}
