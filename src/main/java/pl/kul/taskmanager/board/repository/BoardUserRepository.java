package pl.kul.taskmanager.board.repository;

import io.micrometer.common.KeyValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kul.taskmanager.board.entity.BoardUserEntity;

import java.util.List;
import java.util.Optional;

public interface BoardUserRepository extends JpaRepository<BoardUserEntity, Long> {

    @Query("""
            select bu 
            from BoardUserEntity bu
            join fetch bu.user u
            join fetch bu.board b
            where u.id = :userId 
            and bu.isDefault = true
            """)
    Optional<BoardUserEntity> findByUserIdAndDefaultTrue(Long userId);

    @Query("""
            select bu
            from BoardUserEntity bu
            join fetch bu.user u
            join fetch bu.board b
            where u.id = :userId
            and b.isActive = true
            """)
    List<BoardUserEntity> findActiveBoardsByUserId(Long userId);
}
