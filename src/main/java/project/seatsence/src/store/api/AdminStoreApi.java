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
import project.seatsence.src.store.domain.StoreMember;
import project.seatsence.src.store.domain.TempStore;
import project.seatsence.src.store.dto.AdminStoreMapper;
import project.seatsence.src.store.dto.StoreMemberMapper;
import project.seatsence.src.store.dto.request.*;
import project.seatsence.src.store.dto.response.*;
import project.seatsence.src.store.dto.response.AdminNewBusinessInformationResponse;
import project.seatsence.src.store.service.StoreMemberService;
import project.seatsence.src.store.service.StoreService;
import project.seatsence.src.store.service.StoreSpaceService;
import project.seatsence.src.store.service.TempStoreService;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.dto.response.FindUserByEmailResponse;

@RequestMapping("/v1/stores/admins")
@RestController
@RequiredArgsConstructor
@Tag(name = "03. [Store - Admin]")
@Slf4j
@Validated
public class AdminStoreApi {

    private final TempStoreService tempStoreService;
    private final StoreSpaceService storeSpaceService;
    private final AdminStoreMapper adminStoreMapper;
    private final StoreMemberService storeMemberService;
    private final StoreService storeService;

    @Value("${JWT_SECRET_KEY}")
    private String jwtSecretKey;

    @Operation(summary = "admin이 소유한 모든 가게 정보 가져오기")
    @GetMapping("/owned")
    public AdminOwnedStoreResponse getOwnedStore(@RequestHeader("Authorization") String token) {
        String userEmail = JwtProvider.getUserEmailFromToken(token);
        List<TempStore> ownedTempStore = tempStoreService.findAllOwnedStore(userEmail);
        List<Long> storeIds =
                ownedTempStore.stream().map(TempStore::getId).collect(Collectors.toList());
        return new AdminOwnedStoreResponse(storeIds);
    }

    @Operation(summary = "admin 가게 정보 가져오기")
    @GetMapping("/{store-id}")
    public AdminStoreResponse getStore(@PathVariable("store-id") Long storeId) {
        TempStore tempStore = tempStoreService.findById(storeId);
        return adminStoreMapper.toDto(tempStore);
    }

    @Operation(summary = "admin 가게 정보 등록하기")
    @PostMapping
    public void postStore(
            @RequestBody @Valid AdminStoreCreateRequest adminStoreCreateRequest,
            @RequestHeader("Authorization") String token)
            throws JsonProcessingException {
        String userEmail = JwtProvider.getUserEmailFromToken(token);
        tempStoreService.save(adminStoreCreateRequest, userEmail);
    }

    @Operation(summary = "admin 가게 정보 수정하기")
    @PatchMapping("/{store-id}")
    public void patchStore(
            @PathVariable("store-id") Long storeId,
            @RequestBody @Valid AdminStoreUpdateRequest adminStoreUpdateRequest) {
        tempStoreService.update(storeId, adminStoreUpdateRequest);
    }

    @Operation(summary = "admin 가게 정보 삭제하기")
    @DeleteMapping("/{store-id}")
    public void deleteStore(@PathVariable("store-id") Long storeId) {
        tempStoreService.delete(storeId);
    }

    @Operation(summary = "admin 가게 스페이스 조회하기")
    @GetMapping("/forms/{store-id}")
    public AdminStoreFormResponse getStoreSpace(@PathVariable("store-id") Long storeId) {
        TempStore tempStore = tempStoreService.findById(storeId);
        List<AdminStoreSpaceResponse> adminStoreSpaceResponseList =
                storeSpaceService.getStoreSpace(tempStore);
        return AdminStoreFormResponse.builder()
                .storeId(tempStore.getId())
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
    @PostMapping("/new-business-information/{user-id}")
    public AdminNewBusinessInformationResponse adminNewBusinessInformation(
            @PathVariable("user-id") Long userId,
            @Valid @RequestBody
                    AdminNewBusinessInformationRequest adminNewBusinessInformationRequest) {
        return storeService.adminNewBusinessInformation(userId, adminNewBusinessInformationRequest);
    }
}
