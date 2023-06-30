package project.seatsence.src.admin.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.seatsence.global.entity.BaseTimeAndStateEntity;
import project.seatsence.src.user.domain.User;

public interface AdminRepository extends JpaRepository<User, Long> {
    Boolean existsByEmailAndState(String email, BaseTimeAndStateEntity.State state);

    Boolean existsByNicknameAndState(String nickname, BaseTimeAndStateEntity.State state);

    Optional<User> findByEmail(String email);

    Optional<User> findByIdAndState(Long id, BaseTimeAndStateEntity.State state);
}
