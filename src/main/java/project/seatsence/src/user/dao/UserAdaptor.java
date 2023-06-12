package project.seatsence.src.user.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.seatsence.src.user.domain.User;

@Repository
@RequiredArgsConstructor
public class UserAdaptor {
    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }
}
