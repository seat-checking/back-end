package project.seatsence.src.utilization.api.participation;

import static project.seatsence.global.constants.Constants.*;

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
import project.seatsence.src.utilization.dto.response.participation.ParticipationListResponse;
import project.seatsence.src.utilization.service.participation.ParticipationService;

@RestController
@RequestMapping("/v1/participation")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "11. [Participation]")
public class ParticipationApi {

    private final ParticipationService participationService;

    @Operation(summary = "유저 스페이스 참여 전 내역")
    @GetMapping("/my-list/upcoming")
    public SliceResponse<ParticipationListResponse.ParticipationResponse>
            getUserSpaceUpcomingParticipationList(
                    @RequestHeader(AUTHORIZATION_HEADER) String accessToken,
                    @CookieValue(COOKIE_NAME_PREFIX_SECURE + REFRESH_TOKEN_NAME)
                            String refreshToken,
                    @ParameterObject @PageableDefault(page = 1, size = 15) Pageable pageable) {

        String userEmail = JwtProvider.getUserEmailFromValidToken(accessToken, refreshToken);
        Slice<Participation> participationSlice =
                participationService.getUpcomingParticipation(userEmail, pageable);

        SliceResponse<ParticipationListResponse.ParticipationResponse> sliceResponse =
                participationService.toSliceResponse(participationSlice);

        return sliceResponse;
    }

    @Operation(summary = "유저 스페이스 참여 완료 내역")
    @GetMapping("/my-list/participated")
    public SliceResponse<ParticipationListResponse.ParticipationResponse>
            getUserSpaceParticipatedList(
                    @RequestHeader(AUTHORIZATION_HEADER) String accessToken,
                    @CookieValue(COOKIE_NAME_PREFIX_SECURE + REFRESH_TOKEN_NAME)
                            String refreshToken,
                    @ParameterObject @PageableDefault(page = 1, size = 15) Pageable pageable) {

        String userEmail = JwtProvider.getUserEmailFromValidToken(accessToken, refreshToken);
        Slice<Participation> participationSlice =
                participationService.getParticipated(userEmail, pageable);

        SliceResponse<ParticipationListResponse.ParticipationResponse> sliceResponse =
                participationService.toSliceResponse(participationSlice);

        return sliceResponse;
    }

    @Operation(summary = "유저 예약 취소", description = "유저가 예약했던 좌석 혹은 스페이스의 예약을 취소합니다.")
    @DeleteMapping("/cancel/{participation-id}")
    public void cancelParticipation(@PathVariable("participation-id") Long participationId) {
        participationService.cancelParticipation(participationId);
    }
}
