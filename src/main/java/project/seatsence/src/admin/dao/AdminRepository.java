package project.seatsence.src.admin.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.seatsence.global.entity.BaseTimeAndStateEntity;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.domain.UserRole;

public interface AdminRepository extends JpaRepository<User, Long> {
    Boolean existsByEmailAndState(String email, BaseTimeAndStateEntity.State state);

    Boolean existsByNicknameAndState(String nickname, BaseTimeAndStateEntity.State state);

    Optional<User> findByEmailAndState(String email, BaseTimeAndStateEntity.State state);
    //state랑 신분, 멤버 권한까지

    Optional<User> findByNickname(String nickname);
}
