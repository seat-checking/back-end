package project.seatsence.src.user.service;

import static project.seatsence.global.code.ResponseCode.USER_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.user.dao.UserRepository;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.dto.request.UserSignInRequest;

@Service
@RequiredArgsConstructor
@Transactional
public class UserSignInService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User userSignIn(UserSignInRequest userSignInRequest) {
        User userFoundByEmail =
                userRepository
                        .findByEmail(userSignInRequest.getEmail())
                        .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(
                userSignInRequest.getPassword(), userFoundByEmail.getPassword())) {
            throw new BaseException(USER_NOT_FOUND);
        }

        return userFoundByEmail;
    }
}
