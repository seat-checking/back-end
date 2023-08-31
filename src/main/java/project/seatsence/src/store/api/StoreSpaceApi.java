package project.seatsence.src.store.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.seatsence.src.store.domain.StoreSpace;
import project.seatsence.src.store.dto.request.admin.space.StoreSpaceCreateRequest;
import project.seatsence.src.store.dto.request.admin.space.StoreSpaceUpdateRequest;
import project.seatsence.src.store.dto.response.admin.space.StoreSpaceCreateResponse;
import project.seatsence.src.store.dto.response.admin.space.StoreSpaceResponse;
import project.seatsence.src.store.dto.response.admin.space.StoreSpaceSeatResponse;
import project.seatsence.src.store.service.StoreSpaceService;

@RequestMapping("/v1/stores/admins")
@RestController
@RequiredArgsConstructor
@Tag(name = "03 - 2. [Store - Admin Space]")
@Slf4j
@Validated
public class StoreSpaceApi {

    private final StoreSpaceService storeSpaceService;

    @Operation(summary = "admin 가게의 모든 스페이스 기본정보 불러오기")
    @GetMapping("/spaces/{store-id}")
    public List<StoreSpaceResponse> getStoreSpaceList(@PathVariable("store-id") Long storeId) {
        List<StoreSpace> storeSpaceList = storeSpaceService.findAllByStoreAndState(storeId);
        return storeSpaceList.stream()
                .map(storeSpace -> new StoreSpaceResponse(storeSpace.getId(), storeSpace.getName()))
                .collect(Collectors.toList());
    }

    @Operation(summary = "admin 스페이스의 좌석 정보 불러오기")
    @GetMapping("/spaces/seats/{store-space-id}")
    public StoreSpaceSeatResponse getStoreSpaceSeat(
            @PathVariable("store-space-id") Long storeSpaceId) {
        return storeSpaceService.getStoreSpaceSeat(storeSpaceId);
    }

    @Operation(
            summary = "admin 스페이스의 정보 수정하기",
            description = "예약 단위는 '스페이스', '좌석', '스페이스/좌석' 중 하나로 선택해야 합니다!")
    @PatchMapping("/spaces/{store-space-id}")
    public void putStoreSpaceSeat(
            @PathVariable("store-space-id") Long storeSpaceId,
            @RequestBody StoreSpaceUpdateRequest adminStoreSeatUpdateRequest) {
        storeSpaceService.updateStoreSpace(storeSpaceId, adminStoreSeatUpdateRequest);
    }

    @Operation(summary = "admin 스페이스 삭제")
    @DeleteMapping("/spaces/{store-space-id}")
    public void deleteStoreSpace(@PathVariable("store-space-id") Long storeSpaceId) {
        storeSpaceService.deleteById(storeSpaceId);
    }

    @Operation(
            summary = "admin 가게 스페이스 추가",
            description = "예약 단위는 '스페이스', '좌석', '스페이스/좌석' 중 하나로 선택해야 합니다!")
    @PostMapping("/spaces/{store-id}")
    public StoreSpaceCreateResponse postStoreSpace(
            @PathVariable("store-id") Long storeId,
            @RequestBody @Valid StoreSpaceCreateRequest storeSpaceCreateRequest) {
        return storeSpaceService.save(storeId, storeSpaceCreateRequest);
    }
}
