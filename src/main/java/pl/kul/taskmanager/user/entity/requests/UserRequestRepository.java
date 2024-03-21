package pl.kul.taskmanager.user.entity.requests;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRequestRepository extends JpaRepository<UserRequestEntity, Long>{
}
