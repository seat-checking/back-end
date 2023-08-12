package project.seatsence.src.user.service;

import static project.seatsence.global.code.ResponseCode.USER_NOT_FOUND;
import static project.seatsence.global.constants.Constants.TOKEN_AUTH_TYPE;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.ACTIVE;

import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.config.security.JwtProvider;
import project.seatsence.global.entity.BaseTimeAndStateEntity;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.global.utils.CookieUtils;
import project.seatsence.src.user.dao.UserRepository;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.domain.UserRole;
import project.seatsence.src.user.dto.CustomUserDetailsDto;
import project.seatsence.src.user.dto.request.UserSignInRequest;
import project.seatsence.src.user.dto.request.UserSignUpRequest;
import project.seatsence.src.user.dto.response.UserSignUpResponse;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    private final CookieUtils cookieUtils;

    /*
    findUserByUserEmailAndState : Admin, User 둘 다 사용중
     */
    public User findUserByUserEmailAndState(String email) {
        return userRepository
                .findByEmailAndState(email, BaseTimeAndStateEntity.State.ACTIVE)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));
    }

    public Boolean isUserRoleIsUSER(User user) {
        Boolean result = true;
        UserRole userRole = user.getRole();

        if (!(userRole.equals(UserRole.USER))
                || !(userRepository.existsByIdAndState(
                        user.getId(), ACTIVE))) { // Todo : State를 어디에서 어디까지 체크해야 좋을까?
            result = false;
        }
        return result;
    }

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
                        .name(userSignUpRequest.getName())
                        .birthDate(userSignUpRequest.getBirthDate())
                        .nickname(userSignUpRequest.getNickname())
                        .sex(userSignUpRequest.getSex())
                        .consentToMarketing(userSignUpRequest.getConsentToMarketing())
                        .consentToTermsOfUser(userSignUpRequest.getConsentToTermsOfUser())
                        .build();
        userRepository.save(user);

        return new UserSignUpResponse("회원가입이 완료되었습니다.", user.getId());
    }

    @Transactional(readOnly = true)
    public void signIn(
            UserSignInRequest userSignInRequest,
            HttpServletResponse response,
            String refreshToken,
            User user) {
        String password = userSignInRequest.getPassword();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BaseException(USER_NOT_FOUND);
        }
        cookieUtils.addCookie(response, refreshToken);
    }

    public String issueAccessToken(CustomUserDetailsDto userDetailsDto) {
        return TOKEN_AUTH_TYPE + jwtProvider.generateAccessToken(userDetailsDto);
    }

    public String issueRefreshToken(CustomUserDetailsDto userDetailsDto) {
        return jwtProvider.issueRefreshToken(userDetailsDto); // refresh token은 Bearer 없이
    }

    public User findByIdAndState(Long id) {
        return userRepository
                .findByIdAndState(id, ACTIVE)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));
    }

    public User findByEmailAndState(String email) {
        return userRepository
                .findByEmailAndState(email, ACTIVE)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));
    }
}
