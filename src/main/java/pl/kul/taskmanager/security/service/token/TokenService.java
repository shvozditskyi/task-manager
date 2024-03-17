package pl.kul.taskmanager.security.service.token;

import org.springframework.transaction.annotation.Transactional;

public interface TokenService {

    boolean checkValid(String token);

    @Transactional(rollbackFor = Throwable.class)
    void revoke(String token);
}
