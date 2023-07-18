package project.seatsence.src.store.api;

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
import project.seatsence.src.store.domain.StoreMember;
import project.seatsence.src.store.dto.StoreMemberMapper;
import project.seatsence.src.store.dto.request.StoreMemberRegistrationRequest;
import project.seatsence.src.store.dto.request.StoreMemberUpdateRequest;
import project.seatsence.src.store.dto.response.StoreMemberListResponse;
import project.seatsence.src.store.service.StoreMemberService;

@RequestMapping("/v1/admins/stores")
@RestController
@RequiredArgsConstructor
@Tag(name = "04. [Store - Temp]")
@Slf4j
@Validated
public class AdminStoreTempApi {

    private final StoreMemberService storeMemberService;

    // TODO 관리자 멤버 권한부여 API
    @Operation(summary = "어드민 직원 등록")
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
}
