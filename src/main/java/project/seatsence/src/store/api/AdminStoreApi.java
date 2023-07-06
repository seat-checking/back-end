package project.seatsence.src.store.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.dto.AdminStoreMapper;
import project.seatsence.src.store.dto.request.AdminStoreCreateRequest;
import project.seatsence.src.store.dto.request.AdminStoreFormCreateRequest;
import project.seatsence.src.store.dto.request.AdminStoreUpdateRequest;
import project.seatsence.src.store.dto.response.*;
import project.seatsence.src.store.service.StoreChairService;
import project.seatsence.src.store.service.StoreService;
import project.seatsence.src.store.service.StoreSpaceService;
import project.seatsence.src.store.service.StoreTableService;

@RequestMapping("/v1/admins/stores")
@RestController
@RequiredArgsConstructor
@Tag(name = "03. [Store - Admin]")
@Slf4j
@Validated
public class AdminStoreApi {

    private final StoreService storeService;
    private final StoreSpaceService storeSpaceService;
    private final AdminStoreMapper adminStoreMapper;

    @Operation(summary = "admin 가게 정보 가져오기")
    @GetMapping("/{store-id}")
    public AdminStoreResponse getStore(@PathVariable("store-id") Long storeId) {
        Store store = storeService.findById(storeId);
        return adminStoreMapper.toDto(store);
    }

    @Operation(summary = "admin 가게 정보 등록하기")
    @PostMapping
    public void postStore(@RequestBody @Valid AdminStoreCreateRequest adminStoreCreateRequest) {
        storeService.save(adminStoreCreateRequest);
    }

    @Operation(summary = "admin 가게 정보 수정하기")
    @PatchMapping("/{store-id}")
    public void patchStore(
            @PathVariable("store-id") Long storeId,
            @RequestBody @Valid AdminStoreUpdateRequest adminStoreUpdateRequest) {
        storeService.update(storeId, adminStoreUpdateRequest);
    }

    @Operation(summary = "admin 가게 정보 삭제하기")
    @DeleteMapping("/{store-id}")
    public void deleteStore(@PathVariable("store-id") Long storeId) {
        storeService.delete(storeId);
    }

    @Operation(summary = "admin 가게 형태 조회하기")
    @GetMapping("/forms/{store-id}")
    public AdminStoreFormResponse getStoreForm(@PathVariable("store-id") Long storeId) {
        Store store = storeService.findById(storeId);
        List<AdminStoreSpaceResponse> adminStoreSpaceResponseList =
                storeSpaceService.getStoreForm(store);
        return AdminStoreFormResponse.builder()
                .storeId(store.getId())
                .adminStoreSpaceResponseList(adminStoreSpaceResponseList)
                .build();
    }

    @Operation(summary = "admin 가게 형태 등록하기")
    @PostMapping("/forms/{store-id}")
    public void postStoreForm(
            @PathVariable("store-id") Long storeId,
            @RequestBody List<@Valid AdminStoreFormCreateRequest> adminStoreFormCreateRequestList) {
        storeSpaceService.save(storeId, adminStoreFormCreateRequestList);
    }
}
