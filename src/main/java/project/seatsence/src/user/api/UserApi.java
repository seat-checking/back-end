package project.seatsence.src.user.api;

import static project.seatsence.global.code.ResponseCode.USER_EMAIL_ALREADY_EXIST;
import static project.seatsence.global.code.ResponseCode.USER_NICKNAME_ALREADY_EXIST;
import static project.seatsence.global.constants.Constants.TOKEN_AUTH_TYPE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.seatsence.global.config.security.JwtProvider;
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
    private final JwtProvider jwtProvider;

    @Operation(summary = "이메일 검증 및 중복 확인")
    @PostMapping("/validate/email")
    public ValidateUserInformationResponse validateEmail(
            @Valid @RequestBody ValidateEmailRequest validateEmailRequest) {
        ValidateUserInformationResponse response =
                new ValidateUserInformationResponse(
                        userService.isUsableByEmailDuplicateCheck(validateEmailRequest.getEmail()));
        return response;
    }

    @Operation(summary = "닉네임 검증 및 중복 확인")
    @PostMapping("/validate/nickname")
    public ValidateUserInformationResponse validateNickname(
            @Valid @RequestBody ValidateNicknameRequest validateNicknameRequest) {
        ValidateUserInformationResponse response =
                new ValidateUserInformationResponse(
                        userService.isUsableByNicknameDuplicateCheck(
                                validateNicknameRequest.getNickname()));
        return response;
    }

    @Operation(summary = "유저 회원가입")
    @PostMapping("/sign-up")
    public UserSignUpResponse userSignUp(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
        if (!userService.isUsableByEmailDuplicateCheck(userSignUpRequest.getEmail())) {
            throw new BaseException(USER_EMAIL_ALREADY_EXIST);
        }
        if (!userService.isUsableByNicknameDuplicateCheck(userSignUpRequest.getNickname())) {
            throw new BaseException(USER_NICKNAME_ALREADY_EXIST);
        }
        return userService.userSignUp(userSignUpRequest);
    }

    @Operation(summary = "유저 로그인")
    @PostMapping("/sign-in")
    @Transactional
    public UserSignInResponse userSignIn(@Valid @RequestBody UserSignInRequest userSignInRequest, HttpServletResponse response) {

        User user = userService.findUserByUserEmail(userSignInRequest.getEmail());
        CustomUserDetailsDto userDetailsDto =
                new CustomUserDetailsDto(
                        user.getEmail(),
                        user.getPassword(),
                        user.getState(),
                        user.getNickname(),
                        null);
        String accessToken = TOKEN_AUTH_TYPE + jwtProvider.generateAccessToken(userDetailsDto);
        String refreshToken = TOKEN_AUTH_TYPE + jwtProvider.issueRefreshToken(userDetailsDto);

        userService.signIn(userSignInRequest, response, refreshToken, user);

        return new UserSignInResponse(accessToken);
    }

    @Operation(summary = "일치하는 email의 user검색")
    @GetMapping("/search")
    public FindUserByEmailResponse findUserByEmail(@RequestParam String email) {
        User userFound = userService.findUserByUserEmail(email);
        return new FindUserByEmailResponse(userFound.getEmail(), userFound.getName());
    }
}
