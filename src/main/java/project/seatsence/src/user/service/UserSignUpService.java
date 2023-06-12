package project.seatsence.src.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.src.user.dao.UserRepository;
import project.seatsence.src.user.dto.response.ValidateUserInformationResponse;

import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.ACTIVE;

@Transactional
@RequiredArgsConstructor
@Service
public class UserSignUpService {
    private final UserRepository userRepository;
    public ValidateUserInformationResponse isEmailDuplicated(String email) {
        return new ValidateUserInformationResponse(!userRepository.existsByEmailAndState(email, ACTIVE));
    }

    public ValidateUserInformationResponse isNicknameDuplicated(String nickname) {
        return new ValidateUserInformationResponse(
                !userRepository.existsByNicknameAndState(nickname, ACTIVE));
    }
}
