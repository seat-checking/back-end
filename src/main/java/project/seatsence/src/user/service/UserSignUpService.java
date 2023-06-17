package project.seatsence.src.user.service;

import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.ACTIVE;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.src.user.dao.UserAdaptor;
import project.seatsence.src.user.dao.UserRepository;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.domain.UserRole;
import project.seatsence.src.user.dto.request.UserSignUpRequest;
import project.seatsence.src.user.dto.response.ValidateUserInformationResponse;

@Transactional
@RequiredArgsConstructor
@Service
public class UserSignUpService {
    private final UserRepository userRepository;
    private final UserAdaptor userAdaptor;
    private final PasswordEncoder passwordEncoder;

    public ValidateUserInformationResponse isEmailDuplicated(String email) {
        return new ValidateUserInformationResponse(
                !userRepository.existsByEmailAndState(email, ACTIVE));
    }

    public ValidateUserInformationResponse isNicknameDuplicated(String nickname) {
        return new ValidateUserInformationResponse(
                !userRepository.existsByNicknameAndState(nickname, ACTIVE));
    }

    public void userSignUp(UserSignUpRequest userSignUpReq) {
        User user =
                User.builder()
                        .email(userSignUpReq.getEmail())
                        .password(passwordEncoder.encode(userSignUpReq.getPassword()))
                        .role(UserRole.USER)
                        .employerIdNumber(null)
                        .age(userSignUpReq.getAge())
                        .nickname(userSignUpReq.getNickname())
                        .sex(userSignUpReq.getSex())
                        .consentToMarketing(userSignUpReq.getConsentToMarketing())
                        .consentToTermsOfUser(userSignUpReq.getConsentToTermsOfUser())
                        .build();
        userAdaptor.save(user);
    }
}
