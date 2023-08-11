package project.seatsence.src.store.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.seatsence.global.config.security.JwtProvider;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.domain.StoreMember;
import project.seatsence.src.store.dto.AdminStoreMapper;
import project.seatsence.src.store.dto.StoreMemberMapper;
import project.seatsence.src.store.dto.request.*;
import project.seatsence.src.store.dto.response.*;
import project.seatsence.src.store.dto.response.AdminNewBusinessInformationResponse;
import project.seatsence.src.store.service.StoreMemberService;
import project.seatsence.src.store.service.StoreService;
import project.seatsence.src.store.service.StoreSpaceService;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.dto.response.FindUserByEmailResponse;

@RequestMapping("/v1/stores/admins")
@RestController
@RequiredArgsConstructor
@Tag(name = "03. [Store - Admin]")
@Slf4j
@Validated
public class AdminStoreApi {

    private final StoreSpaceService storeSpaceService;
    private final AdminStoreMapper adminStoreMapper;
    private final StoreMemberService storeMemberService;
    private final StoreService storeService;

    @Value("${JWT_SECRET_KEY}")
    private String jwtSecretKey;

    @Operation(summary = "관리 권한이 있는 모든 가게 정보 가져오기")
    @GetMapping("/owned")
    public AdminOwnedStoreResponse getOwnedStore(@RequestHeader("Authorization") String token) {
        // owner와 member로 있을 때 모두 가게 정보를 가져올 수 있어야함
        String userEmail = JwtProvider.getUserEmailFromToken(token);
        List<StoreMember> storeMemberList = storeService.findAllOwnedStore(userEmail);
        List<Long> storeIds =
                storeMemberList.stream()
                        .map(s -> s.getStore().getId())
                        .collect(Collectors.toList());
        return new AdminOwnedStoreResponse(storeIds);
    }

    @Operation(summary = "admin 가게 정보 가져오기")
    @GetMapping("/{store-id}")
    public AdminStoreResponse getStore(@PathVariable("store-id") Long storeId) {
        Store store = storeService.findByIdAndState(storeId);
        return adminStoreMapper.toDto(store);
    }

    @Operation(summary = "admin 가게 기본 정보 등록하기")
    @PostMapping("/basic-information/{store-id}")
    public void postStoreBasicInformation(
            @PathVariable("store-id") Long storeId,
            @RequestBody @Valid AdminStoreBasicInformationRequest request) {
        storeService.updateBasicInformation(request, storeId);
    }

    @Operation(summary = "admin 가게 운영 시간 정보 등록하기")
    @PostMapping("/operating-time/{store-id}")
    public void postStoreOperatingTime(
            @PathVariable("store-id") Long storeId,
            @RequestBody @Valid AdminStoreOperatingTimeRequest request) {
        storeService.updateOperatingTime(request, storeId);
    }

    @Operation(summary = "admin 가게 정보 삭제하기")
    @DeleteMapping("/{store-id}")
    public void deleteStore(@PathVariable("store-id") Long storeId) {
        storeService.delete(storeId);
    }

    @Operation(summary = "admin 가게 스페이스 조회하기")
    @GetMapping("/forms/{store-id}")
    public AdminStoreFormResponse getStoreSpace(@PathVariable("store-id") Long storeId) {
        Store store = storeService.findByIdAndState(storeId); // storeId가 존재하는지 확인(존재하지 않으면 예외 발생
        List<AdminStoreSpaceResponse> adminStoreSpaceResponseList =
                storeSpaceService.getStoreSpace(store);
        return AdminStoreFormResponse.builder()
                .storeId(store.getId())
                .adminStoreSpaceResponseList(adminStoreSpaceResponseList)
                .build();
    }

    @Operation(
            summary = "admin 가게 스페이스 등록하기",
            description = "예약 단위는 '스페이스', '좌석', '스페이스/좌석' 중 하나로 선택해야 합니다!")
    @PostMapping("/forms/{store-id}")
    public void postStoreSpace(
            @PathVariable("store-id") Long storeId,
            @RequestBody List<@Valid AdminStoreFormCreateRequest> adminStoreFormCreateRequestList) {
        storeSpaceService.save(storeId, adminStoreFormCreateRequestList);
    }

    @Operation(summary = "직원 등록을 위한 유저 검색")
    @GetMapping("/member-registration/{store-id}/search")
    public FindUserByEmailResponse findUserByEmail(
            @PathVariable("store-id") Long storeId, @RequestParam String email) {
        User user = storeMemberService.findUserByEmail(email);
        return new FindUserByEmailResponse(user.getEmail(), user.getName());
    }

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

    @Operation(summary = "어드민 사업자정보 추가")
    @PostMapping("/new-business-information")
    public AdminNewBusinessInformationResponse adminNewBusinessInformation(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody
                    AdminNewBusinessInformationRequest adminNewBusinessInformationRequest) {
        String userEmail = JwtProvider.getUserEmailFromToken(token);
        return storeService.adminNewBusinessInformation(
                userEmail, adminNewBusinessInformationRequest);
    }

    @Operation(summary = "가게 별 권한 가져오기")
    @GetMapping("/permission/{store-id}")
    public AdminStorePermissionResponse getPermissionByMenu(
            @PathVariable("store-id") Long storeId, @RequestHeader("Authorization") String token) {
        String userEmail = JwtProvider.getUserEmailFromToken(token);
        String permissionByMenu = storeMemberService.getPermissionByMenu(storeId, userEmail);
        return new AdminStorePermissionResponse(permissionByMenu);
    }
}
