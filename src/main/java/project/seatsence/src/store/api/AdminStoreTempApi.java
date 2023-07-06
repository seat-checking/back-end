package project.seatsence.src.store.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.dto.request.StoreMemberRegistrationRequest;
import project.seatsence.src.store.dto.response.StoreMemberListResponse;
import project.seatsence.src.store.service.StoreMemberService;
import project.seatsence.src.store.service.StoreService;

import javax.validation.Valid;

@RequestMapping("/v1/admins/stores")
@RestController
@RequiredArgsConstructor
@Tag(name = "04. [Store - Temp]")
@Slf4j
@Validated
public class AdminStoreTempApi {

    private final StoreMemberService storeMemberService;
    private final StoreService storeService;

    @Operation(summary = "어드민 직원 리스트")
    @GetMapping("/member-registration/{store-id}")
    public StoreMemberListResponse getStoreMember(@PathVariable("store-id") Long storeId) {
        Store store = storeService.findById(storeId);
        return StoreMemberListResponse.builder().build(); //이거 리스트 뽑아서 반환
    }

    // TODO 관리자 멤버 권한부여 API
    @Operation(summary = "어드민 직원 등록")
    @PostMapping("/member-registration/{store-id}")
    public void adminMemberRegistration(
            @PathVariable("store-id") Long storeId,
            @Valid @RequestBody StoreMemberRegistrationRequest storeMemberRegistrationRequest) {
        storeMemberService.storeMemberRegistration(storeId, storeMemberRegistrationRequest);
    }
}
