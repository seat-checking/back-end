package project.seatsence.src.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.code.ResponseCode;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.user.dao.UserRepository;
import project.seatsence.src.user.domain.User;

@Service
@Transactional
@RequiredArgsConstructor
public class FindUserService {
    private final UserRepository userRepository;

    public User findUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new BaseException(ResponseCode.USER_NOT_FOUND));
    }
}
