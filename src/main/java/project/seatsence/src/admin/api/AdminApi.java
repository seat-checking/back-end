package project.seatsence.src.admin.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.seatsence.src.admin.dto.request.AdminSignInRequest;
import project.seatsence.src.admin.dto.request.AdminSignUpRequest;
import project.seatsence.src.admin.dto.response.AdminSignInResponse;
import project.seatsence.src.admin.service.AdminService;
import project.seatsence.src.store.domain.StoreMember;
import project.seatsence.src.store.service.StoreMemberService;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.dto.CustomUserDetailsDto;
import project.seatsence.src.user.dto.request.ValidateEmailRequest;
import project.seatsence.src.user.dto.request.ValidateNicknameRequest;
import project.seatsence.src.user.dto.response.ValidateUserInformationResponse;
import project.seatsence.src.user.service.UserService;

@RestController
@RequestMapping("/v1/admins")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "02. [Admin]")
public class AdminApi {
    private final AdminService adminService;
    private final UserService userService;
    private final StoreMemberService storeMemberService;

    @Operation(summary = "어드민 회원가입")
    @PostMapping("/sign-up")
    public void adminSignUp(@Valid @RequestBody AdminSignUpRequest adminSignUpRequest) {
        adminService.adminSignUp(adminSignUpRequest);
    }

    @Operation(summary = "어드민 회원가입 이메일 중복 검사")
    @PostMapping("/validate/email")
    public ValidateUserInformationResponse validateEmail(
            @Valid @RequestBody ValidateEmailRequest validateEmailRequest) {
        ValidateUserInformationResponse response =
                new ValidateUserInformationResponse(
                        userService.isUsableByEmailDuplicateCheck(validateEmailRequest.getEmail()));
        return response;
    }

    @Operation(summary = "어드민 닉네임 검증 및 중복 확인")
    @PostMapping("/validate/nickname")
    public ValidateUserInformationResponse validateNickname(
            @Valid @RequestBody ValidateNicknameRequest validateNicknameRequest) {
        ValidateUserInformationResponse response =
                new ValidateUserInformationResponse(
                        userService.isUsableByNicknameDuplicateCheck(
                                validateNicknameRequest.getNickname()));
        return response;
    }

    @Operation(summary = "어드민 로그인")
    @PostMapping("/sign-in")
    public AdminSignInResponse adminSignIn(
            @Valid @RequestBody AdminSignInRequest adminSignInRequest,
            HttpServletResponse response) {
        User user = adminService.findAdmin(adminSignInRequest);

        CustomUserDetailsDto userDetailsDto =
                new CustomUserDetailsDto(
                        user.getEmail(),
                        user.getPassword(),
                        user.getState(),
                        user.getNickname(),
                        null);
        String accessToken = userService.issueAccessToken(userDetailsDto);
        String refreshToken = userService.issueRefreshToken(userDetailsDto);

        adminService.adminSignIn(response, refreshToken);

        return new AdminSignInResponse(accessToken);
    }
}
