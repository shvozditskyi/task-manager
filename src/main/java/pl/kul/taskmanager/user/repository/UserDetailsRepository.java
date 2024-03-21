package pl.kul.taskmanager.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kul.taskmanager.user.entity.UserDetailsEntity;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Long> {

    @Query("""
                select u from UserDetailsEntity ud
                join fetch ud.user u
                where u.email = :email  
            """)
    Optional<UserDetailsEntity> findByEmail(String email);
}
