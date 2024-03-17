package pl.kul.taskmanager.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kul.taskmanager.security.entity.TokenEntity;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    @Query("select t from TokenEntity t where t.token = :token and t.active = true")
    Optional<TokenEntity> findByTokenAndActive(String token);

    @Query("update TokenEntity t set t.active = false where t.token = :token")
    void revoke(String token);
}
