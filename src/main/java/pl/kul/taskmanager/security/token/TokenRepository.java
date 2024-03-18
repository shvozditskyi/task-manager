package pl.kul.taskmanager.security.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    @Query("select t from TokenEntity t where t.token = :token and t.active = true")
    Optional<TokenEntity> findByTokenAndActive(String token);

    @Modifying
    @Query("delete from TokenEntity t where t.token = :token")
    void revoke(String token);

    @Query("select t.token from TokenEntity t where t.user.id = :userId and t.active = true")
    Optional<String> findByUserId(Long userId);
}
