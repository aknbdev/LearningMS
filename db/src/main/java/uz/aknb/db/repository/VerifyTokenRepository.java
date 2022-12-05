package uz.aknb.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.aknb.db.entity.EntVerifyToken;

import java.util.Optional;

@Repository
public interface VerifyTokenRepository extends JpaRepository<EntVerifyToken, Long> {
    @Query("select e from EntVerifyToken e where e.token = ?1")
    Optional<EntVerifyToken> findByToken(String token);
}
