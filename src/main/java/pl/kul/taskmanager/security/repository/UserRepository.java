package pl.kul.taskmanager.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kul.taskmanager.security.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
