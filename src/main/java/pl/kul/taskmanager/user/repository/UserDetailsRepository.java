package pl.kul.taskmanager.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kul.taskmanager.user.entity.UserDetailsEntity;

public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Long> {
}
