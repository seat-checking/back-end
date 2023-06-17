package project.seatsence.src.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.user.dto.request.UserSignInRequest;
import project.seatsence.src.user.dto.request.UserSignUpRequest;
import project.seatsence.src.user.dto.request.ValidateEmailRequest;
import project.seatsence.src.user.dto.request.ValidateNicknameRequest;
import project.seatsence.src.user.dto.response.UserSignInResponse;
import project.seatsence.src.user.dto.response.UserSignUpResponse;
import project.seatsence.src.user.dto.response.ValidateUserInformationResponse;
import project.seatsence.src.user.service.UserSignInService;
import project.seatsence.src.user.service.UserSignUpService;

@RestController
@RequestMapping("/v1/users")
@Tag(name = "01. [User]")
@Validated
@RequiredArgsConstructor
public class UserApi {

    private final UserSignUpService userSignUpService;
    private final UserSignInService userSignInService;

    @Operation(summary = "이메일 검증 및 중복 확인")
    @PostMapping("/validate/email")
    public ValidateUserInformationResponse validateEmail(
            @Valid @RequestBody ValidateEmailRequest validateEmailRequest) {
        return userSignUpService.isUsableByEmailDuplicateCheck(validateEmailRequest.getEmail());
    }

    @Operation(summary = "닉네임 검증 및 중복 확인")
    @PostMapping("/validate/nickname")
    public ValidateUserInformationResponse validateNickname(
            @Valid @RequestBody ValidateNicknameRequest validateNicknameReq) {
        return userSignUpService.isUsableByNicknameDuplicateCheck(validateNicknameReq.getNickname());
    }

    @Operation(summary = "유저 회원가입")
    @PostMapping("/sign-up")
    public UserSignUpResponse userSignUp(@Valid @RequestBody UserSignUpRequest userSignUpReq) {

        return userSignUpService.userSignUp(userSignUpReq);
    }

    @Operation(summary = "유저 로그인")
    @PostMapping("/sign-in")
    public UserSignInResponse userSignIn(@Valid @RequestBody UserSignInRequest userSignInRequest) {

        return userSignInService.userSignIn(userSignInRequest);
    }
}
