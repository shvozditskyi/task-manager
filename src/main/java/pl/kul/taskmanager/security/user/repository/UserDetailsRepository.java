package pl.kul.taskmanager.security.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kul.taskmanager.security.user.entity.UserDetailsEntity;

public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Long> {
}
