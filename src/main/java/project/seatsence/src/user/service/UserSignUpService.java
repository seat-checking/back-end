package project.seatsence.src.user.service;

import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.ACTIVE;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.src.user.dao.UserRepository;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.domain.UserRole;
import project.seatsence.src.user.dto.request.UserSignUpRequest;
import project.seatsence.src.user.dto.response.UserSignUpResponse;

@Transactional
@RequiredArgsConstructor
@Service
public class UserSignUpService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Boolean isUsableByEmailDuplicateCheck(String email) {
        return !userRepository.existsByEmailAndState(email, ACTIVE);
    }

    public Boolean isUsableByNicknameDuplicateCheck(String nickname) {
        return !userRepository.existsByNicknameAndState(nickname, ACTIVE);
    }

    public UserSignUpResponse userSignUp(UserSignUpRequest userSignUpRequest) {
        User user =
                User.builder()
                        .email(userSignUpRequest.getEmail())
                        .password(passwordEncoder.encode(userSignUpRequest.getPassword()))
                        .role(UserRole.USER)
                        .age(userSignUpRequest.getAge())
                        .nickname(userSignUpRequest.getNickname())
                        .sex(userSignUpRequest.getSex())
                        .consentToMarketing(userSignUpRequest.getConsentToMarketing())
                        .consentToTermsOfUser(userSignUpRequest.getConsentToTermsOfUser())
                        .build();
        userRepository.save(user);

        return new UserSignUpResponse("회원가입이 완료되었습니다.", user.getId());
    }
}
