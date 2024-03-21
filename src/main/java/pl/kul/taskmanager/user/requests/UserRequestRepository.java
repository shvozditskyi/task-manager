package pl.kul.taskmanager.user.requests;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRequestRepository extends JpaRepository<UserRequestEntity, Long>{
}
