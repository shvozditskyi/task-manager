package pl.kul.taskmanager.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kul.taskmanager.board.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}
