package pl.kul.taskmanager.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kul.taskmanager.board.entity.BoardUserEntity;

import java.util.List;
import java.util.Optional;

public interface BoardUserRepository extends JpaRepository<BoardUserEntity, Long> {

    @Query("""
            select bu
            from BoardUserEntity bu
            join bu.user u
            join bu.board b
            where u.id = :userId
            and b.isActive = true
            """)
    List<BoardUserEntity> findActiveBoardsByUserId(Long userId);

    @Query("""
            select bu
            from BoardUserEntity bu
            join bu.user u
            join bu.board b
            where u.id = :userId
            and b.id = :boardId
            """)
    Optional<BoardUserEntity> findByUserIdAndBoardId(Long userId, Long boardId);

    @Query("""
            select bu
            from BoardUserEntity bu
            join bu.user u
            join bu.board b
            where u.id = :userId
            and b.id = :boardId
            and bu.isOwner = true
            """)
    Optional<BoardUserEntity> findByUserIdAndIsOwnerAndBoardId(Long userId, Long boardId);
}
