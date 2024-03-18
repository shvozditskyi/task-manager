package pl.kul.taskmanager.security.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kul.taskmanager.security.user.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String username);
}
