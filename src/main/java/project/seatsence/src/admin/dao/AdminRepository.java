package project.seatsence.src.admin.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.seatsence.src.user.domain.User;

public interface AdminRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
