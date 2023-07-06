package project.seatsence.src.auth.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.seatsence.src.auth.domain.RefreshToken;
import project.seatsence.src.user.domain.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByUser(User user);
}
