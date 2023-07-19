package project.seatsence.src.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.seatsence.global.code.ResponseCode;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.user.dao.UserRepository;
import project.seatsence.src.user.domain.User;

@Service
@RequiredArgsConstructor
public class UserAdaptorImpl implements UserAdaptor {

    private final UserRepository userRepository;

    @Autowired private UserService userService;

    @Override
    public User findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new BaseException(ResponseCode.USER_NOT_FOUND));
    }

    @Override
    public User findByEmail(String email) {
        return userService.findUserByUserEmail(email);
    }
}
