package project.seatsence.src.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project.seatsence.src.user.domain.User;
import project.seatsence.global.entity.BaseTimeAndStateEntity.State;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Boolean existsByEmailAndState(String email, State state);
    Boolean existsByNicknameAndState(String nickname, State state);
}
