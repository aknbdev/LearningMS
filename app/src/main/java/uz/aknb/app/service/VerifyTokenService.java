package uz.aknb.app.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.aknb.app.exception.ExceptionHandler;
import uz.aknb.app.exception.ExceptionType;
import uz.aknb.db.entity.EntUser;
import uz.aknb.db.entity.EntVerifyToken;
import uz.aknb.db.repository.VerifyTokenRepository;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class VerifyTokenService implements ExceptionHandler {

    private final VerifyTokenRepository verifyTokenRepository;


    public String  create(EntUser user) {

        String token = UUID.randomUUID().toString();

        EntVerifyToken verifyToken = new EntVerifyToken(token, user, LocalDateTime.now().plusMinutes(15));

        verifyTokenRepository.save(verifyToken);

        return token;
    }

    public EntUser isValidToken(String token) {
        EntVerifyToken verifyToken = verifyTokenRepository.findByToken(token).orElseThrow(
                ()-> exception(ExceptionType.ENTITY_NOT_FOUND,"verify.token")
        );

        if (verifyToken.getExpiresDate().isBefore(LocalDateTime.now())) {
            log.error("Verify token expired user mail: {}", verifyToken.getUser().getEmail());
            throw exception(ExceptionType.EXPIRED, "verify.token");
        }
        return verifyToken.getUser();
    }
}
