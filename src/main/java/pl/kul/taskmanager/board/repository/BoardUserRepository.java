package pl.kul.taskmanager.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kul.taskmanager.board.entity.BoardUserEntity;

import java.util.Optional;

public interface BoardUserRepository extends JpaRepository<BoardUserEntity, Long> {

    @Query("SELECT bu FROM BoardUserEntity bu WHERE bu.user.id = ?1 AND bu.isDefault = true")
    Optional<BoardUserEntity> findByUserIdAndDefaultTrue(Long userId);
}
