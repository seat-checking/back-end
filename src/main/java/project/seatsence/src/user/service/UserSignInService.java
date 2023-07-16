package project.seatsence.src.user.service;

import static project.seatsence.global.code.ResponseCode.USER_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.user.dao.UserRepository;
import project.seatsence.src.user.domain.User;

@Service
@RequiredArgsConstructor
@Transactional
public class UserSignInService {
    private final UserRepository userRepository;

    public User findUserByUserEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

    }
}
