package project.seatsence.src.user.api;

import static project.seatsence.global.code.ResponseCode.USER_EMAIL_ALREADY_EXIST;
import static project.seatsence.global.code.ResponseCode.USER_NICKNAME_ALREADY_EXIST;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.seatsence.global.code.ResponseCode;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.dto.CustomUserDetailsDto;
import project.seatsence.src.user.dto.request.*;
import project.seatsence.src.user.dto.response.FindUserByEmailResponse;
import project.seatsence.src.user.dto.response.UserSignInResponse;
import project.seatsence.src.user.dto.response.UserSignUpResponse;
import project.seatsence.src.user.dto.response.ValidateUserInformationResponse;
import project.seatsence.src.user.service.UserService;

@RestController
@RequestMapping("/v1/users")
@Tag(name = "01. [User]")
@Validated
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @Operation(
            summary = "이메일 검증 및 중복 확인",
            description = "회원가입시, 사용하려는 이메일이 이미 사용되고있는 이메일인지 중복검사와 이메일 형식 검사를 진행합니다.")
    @PostMapping("/validate/email")
    public ValidateUserInformationResponse validateEmail(
            @Valid @RequestBody ValidateEmailRequest validateEmailRequest) {
        ValidateUserInformationResponse response =
                new ValidateUserInformationResponse(
                        userService.isUsableByEmailDuplicateCheck(validateEmailRequest.getEmail()));
        return response;
    }

    @Operation(
            summary = "닉네임 검증 및 중복 확인",
            description = "회원가입시, 사용하려는 닉네임이 이미 사용되고있는 닉네임인지 중복검사와 닉네임 형식 검사를 진행합니다.")
    @PostMapping("/validate/nickname")
    public ValidateUserInformationResponse validateNickname(
            @Valid @RequestBody ValidateNicknameRequest validateNicknameRequest) {
        ValidateUserInformationResponse response =
                new ValidateUserInformationResponse(
                        userService.isUsableByNicknameDuplicateCheck(
                                validateNicknameRequest.getNickname()));
        return response;
    }

    @Operation(summary = "유저 회원가입", description = "유저가 회원가입을 합니다.")
    @PostMapping("/sign-up")
    public UserSignUpResponse userSignUp(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
        if (!userService.isUsableByEmailDuplicateCheck(userSignUpRequest.getEmail())) {
            throw new BaseException(USER_EMAIL_ALREADY_EXIST); // Todo : 예외는 Service단에서 던지는게 좋을까?
        }
        if (!userService.isUsableByNicknameDuplicateCheck(userSignUpRequest.getNickname())) {
            throw new BaseException(USER_NICKNAME_ALREADY_EXIST);
        }
        return userService.userSignUp(userSignUpRequest);
    }

    @Operation(summary = "유저 로그인", description = "유저가 로그인을 합니다.")
    @PostMapping("/sign-in")
    @Transactional
    public UserSignInResponse userSignIn(
            @Valid @RequestBody UserSignInRequest userSignInRequest, HttpServletResponse response) {

        User user = userService.findUserByUserEmailAndState(userSignInRequest.getEmail());

        if (!userService.isUserRoleIsUSER(user)) {
            throw new BaseException(ResponseCode.USER_NOT_FOUND);
        }

        CustomUserDetailsDto userDetailsDto =
                new CustomUserDetailsDto(
                        user.getEmail(),
                        user.getPassword(),
                        user.getState(),
                        user.getNickname(),
                        null);
        String accessToken = userService.issueAccessToken(userDetailsDto);
        String refreshToken = userService.issueRefreshToken(userDetailsDto);

        userService.signIn(userSignInRequest, response, refreshToken, user);

        return new UserSignInResponse(accessToken);
    }

    @Operation(
            summary = "일치하는 email의 user검색",
            description = "검색하고자 하는 email을 가진 유저를 검색합니다. (관리자는 검색되지 않습니다.)")
    @GetMapping("/search")
    public FindUserByEmailResponse findUserByEmail(
            @Parameter(name = "이메일", in = ParameterIn.QUERY, example = "test@naver.com")
                    @RequestParam
                    String email) {
        User userFound = userService.findUserByUserEmailAndState(email);
        return new FindUserByEmailResponse(userFound.getEmail());
    }
}
