package project.seatsence.src.user.service;

import static project.seatsence.global.code.ResponseCode.USER_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.entity.BaseTimeAndStateEntity;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.user.dao.UserRepository;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.domain.UserRole;
import project.seatsence.src.user.dto.request.UserSignInRequest;
import project.seatsence.src.user.dto.request.UserSignUpRequest;
import project.seatsence.src.user.dto.response.UserSignUpResponse;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User findUserByUserEmail(String email) {
        return userRepository
                .findByEmailAndState(email, BaseTimeAndStateEntity.State.ACTIVE)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));
    }

    public Boolean isUsableByEmailDuplicateCheck(String email) {
        return !userRepository.existsByEmail(email);
    }

    public Boolean isUsableByNicknameDuplicateCheck(String nickname) {
        return !userRepository.existsByNickname(nickname);
    }

    public UserSignUpResponse userSignUp(UserSignUpRequest userSignUpRequest) {
        User user =
                User.builder()
                        .email(userSignUpRequest.getEmail())
                        .password(passwordEncoder.encode(userSignUpRequest.getPassword()))
                        .role(UserRole.USER)
                        .name(userSignUpRequest.getName())
                        .age(userSignUpRequest.getAge())
                        .nickname(userSignUpRequest.getNickname())
                        .sex(userSignUpRequest.getSex())
                        .consentToMarketing(userSignUpRequest.getConsentToMarketing())
                        .consentToTermsOfUser(userSignUpRequest.getConsentToTermsOfUser())
                        .build();
        userRepository.save(user);

        return new UserSignUpResponse("회원가입이 완료되었습니다.", user.getId());
    }


    @Transactional(readOnly = true)
    public void signIn(UserSignInRequest userSignInRequest, HttpServletResponse response, String refreshToken, User user) {
        String password = userSignInRequest.getPassword();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BaseException(USER_NOT_FOUND);
        }
    }
}
