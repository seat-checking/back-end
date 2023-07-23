package project.seatsence.src.user.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.seatsence.global.entity.BaseTimeAndStateEntity.*;
import project.seatsence.src.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    Boolean existsByNickname(String nickname);

    User save(User user);

    Optional<User> findByEmailAndState(String email, State state);

    Boolean existsByUserIdAndState(Long userId, State state);
}
