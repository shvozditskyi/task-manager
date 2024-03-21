package pl.kul.taskmanager.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kul.taskmanager.board.entity.BoardEntity;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    @Query("""
            select bu from BoardUserEntity bu
            join fetch bu.board b
            join fetch bu.user u
            where b.id = :boardId
            and u.id = :userId
            and b.isActive = true
            """)
    Optional<BoardEntity> findActiveBoardByBoardIdAndUserId(Long boardId, Long userId);
}
