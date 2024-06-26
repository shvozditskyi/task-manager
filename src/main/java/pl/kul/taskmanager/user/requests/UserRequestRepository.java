package pl.kul.taskmanager.user.requests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRequestRepository extends JpaRepository<UserRequestEntity, Long>{
    @Query("""
            select ur
            from UserRequestEntity ur
            join ur.receiver u
            join u.user user
            where ur.id = :requestId
            and user.id = :userId
            """)
    Optional<UserRequestEntity> findByIdAndReceiverId(Long requestId, Long userId);
    @Query("""
            select ur
            from UserRequestEntity ur
            join ur.receiver ud join ud.user u
            where u.email = :receiverEmail
            and ur.isActive = true
            """)
    Optional<UserRequestEntity> findByReceiverEmailAndIsActive(String receiverEmail);

    @Query("""
            select ur
            from UserRequestEntity ur
            join ur.sender u
            join ur.receiver r
            join r.user ru
            where ur.id = :requestId
            and (u.id = :userId or ru.id = :userId)
            """)
    Optional<UserRequestEntity> findByIdAndSenderReceiverId(Long requestId, Long userId);

    @Modifying
    @Query("""
            delete from UserRequestEntity ur
            where ur.id = :requestId
            and ur.sender.id = :userId
            """)
    Long deleteByIdAndSenderReceiverId(Long requestId, Long userId);

    @Query("""
            select ur
            from UserRequestEntity ur
            join ur.receiver u
            join u.user user
            where user.id = :userId
            and ur.requestStatus = 'PENDING'
            """)
    List<UserRequestEntity> findAllByReceiverId(Long userId);
}
