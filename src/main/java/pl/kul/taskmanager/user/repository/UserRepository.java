package pl.kul.taskmanager.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kul.taskmanager.user.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String username);
}
