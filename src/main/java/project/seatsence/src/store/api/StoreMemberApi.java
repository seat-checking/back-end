package project.seatsence.src.store.api;

import static project.seatsence.global.constants.Constants.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.seatsence.global.config.security.JwtProvider;
import project.seatsence.src.store.domain.StoreMember;
import project.seatsence.src.store.dto.mapper.StoreMemberMapper;
import project.seatsence.src.store.dto.request.admin.member.StoreMemberRegistrationRequest;
import project.seatsence.src.store.dto.request.admin.member.StoreMemberUpdateRequest;
import project.seatsence.src.store.dto.response.admin.basic.StorePermissionResponse;
import project.seatsence.src.store.dto.response.admin.member.StoreMemberListResponse;
import project.seatsence.src.store.service.StoreMemberService;

@RequestMapping("/v1/stores/admins")
@RestController
@RequiredArgsConstructor
@Tag(name = "03 - 3. [Store - Admin Member]")
@Slf4j
@Validated
public class StoreMemberApi {

    private final StoreMemberService storeMemberService;

    @Operation(summary = "admin 직원 등록")
    @PostMapping("/member-registration/{store-id}")
    public void registerStoreMember(
            @PathVariable("store-id") Long storeId,
            @Valid @RequestBody StoreMemberRegistrationRequest storeMemberRegistrationRequest)
            throws JsonProcessingException {

        storeMemberService.storeMemberRegistration(storeId, storeMemberRegistrationRequest);
    }

    @Operation(summary = "가게 직원 리스트")
    @GetMapping("/member-registration/{store-id}")
    public StoreMemberListResponse getStoreMember(@PathVariable("store-id") Long storeId) {

        List<StoreMember> storeMembers = storeMemberService.findAllByStoreIdAndPosition(storeId);

        List<StoreMemberListResponse.StoreMemberResponse> storeMemberResponseList =
                storeMembers.stream()
                        .map(StoreMemberMapper::toStoreMemberResponse)
                        .collect(Collectors.toList());

        return StoreMemberListResponse.builder()
                .storeMemberResponseList(storeMemberResponseList)
                .build();
    }

    @Operation(summary = "직원 권한 수정")
    @PatchMapping("/member-registration/{store-id}")
    public void updateStoreMember(
            @PathVariable("store-id") Long storeId,
            @Valid @RequestBody StoreMemberUpdateRequest storeMemberUpdateRequest)
            throws JsonProcessingException {
        storeMemberService.update(storeId, storeMemberUpdateRequest);
    }

    @Operation(summary = "직원 삭제")
    @DeleteMapping("/member-registration/{store-id}")
    public void deleteStoreMember(
            @PathVariable("store-id") Long storeId,
            @Valid @RequestParam("member-id") Long storeMemberAuthorityId) {
        storeMemberService.delete(storeMemberAuthorityId);
    }

    @Operation(summary = "가게 별 권한 가져오기")
    @GetMapping("/permission/{store-id}")
    public StorePermissionResponse getPermissionByMenu(
            @PathVariable("store-id") Long storeId,
            @RequestHeader(AUTHORIZATION_HEADER) String accessToken,
            @CookieValue(COOKIE_NAME_PREFIX_SECURE + REFRESH_TOKEN_NAME) String refreshToken) {
        String userEmail = JwtProvider.getUserEmailFromValidToken(accessToken, refreshToken);
        String permissionByMenu = storeMemberService.getPermissionByMenu(storeId, userEmail);
        return new StorePermissionResponse(permissionByMenu);
    }
}
