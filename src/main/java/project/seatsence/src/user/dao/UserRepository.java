package project.seatsence.src.user.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.seatsence.global.entity.BaseTimeAndStateEntity.*;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.domain.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmailAndState(String email, State state);

    Boolean existsByNicknameAndState(String nickname, State state);

    User save(User user);

    Optional<User> findByEmailAndRoleAndState(String email, UserRole role, State state);

    Optional<User> findByEmailAndState(String email, State state);

    Boolean existsByIdAndState(Long id, State state);

    Optional<User> findByIdAndState(Long id, State state);
}
