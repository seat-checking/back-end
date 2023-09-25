package project.seatsence.src.utilization.api.participation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import project.seatsence.global.config.security.JwtProvider;
import project.seatsence.global.response.SliceResponse;
import project.seatsence.src.utilization.domain.Participation.Participation;
import project.seatsence.src.utilization.dto.response.participation.UserParticipationListResponse;
import project.seatsence.src.utilization.service.UserUtilizationService;
import project.seatsence.src.utilization.service.participation.UserParticipationService;

import static project.seatsence.global.constants.Constants.*;

@RestController
@RequestMapping("/v1/participation/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "11. [Participation - User]")
public class UserParticipationApi {

    private final UserParticipationService userParticipationService;

    @Operation(summary = "유저 스페이스 참여 전 내역")
    @GetMapping("/my-list/upcoming")
    public SliceResponse<UserParticipationListResponse.ParticipationResponse> getUserSpaceUpcomingParticipationList(
            @RequestHeader(AUTHORIZATION_HEADER) String accessToken,
            @CookieValue(COOKIE_NAME_PREFIX_SECURE + REFRESH_TOKEN_NAME) String refreshToken,
            @ParameterObject @PageableDefault(page = 1, size = 15) Pageable pageable) {

        String userEmail = JwtProvider.getUserEmailFromValidToken(accessToken, refreshToken);
        Slice<Participation> participationSlice = userParticipationService.getUpcomingParticipation(userEmail, pageable);

        SliceResponse<UserParticipationListResponse.ParticipationResponse> sliceResponse =
                userParticipationService.toSliceResponse(participationSlice);

        return sliceResponse;
    }

    @Operation(summary = "유저 스페이스 참여 후 내역")
    @GetMapping("/my-list/participated")
    public SliceResponse<UserParticipationListResponse.ParticipationResponse> getUserSpaceParticipatedList(
            @RequestHeader(AUTHORIZATION_HEADER) String accessToken,
            @CookieValue(COOKIE_NAME_PREFIX_SECURE + REFRESH_TOKEN_NAME) String refreshToken,
            @ParameterObject @PageableDefault(page = 1, size = 15) Pageable pageable) {

        String userEmail = JwtProvider.getUserEmailFromValidToken(accessToken, refreshToken);
        Slice<Participation> participationSlice = userParticipationService.getParticipated(userEmail, pageable);

        SliceResponse<UserParticipationListResponse.ParticipationResponse> sliceResponse =
                userParticipationService.toSliceResponse(participationSlice);

        return sliceResponse;
    }
}
