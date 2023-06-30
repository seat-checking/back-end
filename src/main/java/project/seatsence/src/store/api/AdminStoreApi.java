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
import project.seatsence.src.store.dto.response.AdminStoreResponse;
import project.seatsence.src.store.service.StoreService;
import project.seatsence.src.store.service.StoreSpaceService;

@RequestMapping("/v1/admin/store")
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
    @GetMapping("/{id}")
    public AdminStoreResponse getStore(@PathVariable Long id) {
        Store store = storeService.findById(id);
        return adminStoreMapper.toDto(store);
    }

    @Operation(summary = "admin 가게 정보 등록하기")
    @PostMapping
    public void postStore(@RequestBody @Valid AdminStoreCreateRequest adminStoreCreateRequest) {
        storeService.save(adminStoreCreateRequest);
    }

    @Operation(summary = "관리자 가게 정보 수정하기")
    @PatchMapping("/{id}")
    public void patchStore(
            @PathVariable Long id,
            @RequestBody @Valid AdminStoreUpdateRequest adminStoreUpdateRequest) {
        storeService.update(id, adminStoreUpdateRequest);
    }

    @Operation(summary = "관리자 가게 정보 삭제하기")
    @DeleteMapping("/{id}")
    public void deleteStore(@PathVariable Long id) {
        storeService.delete(id);
    }

    @Operation(summary = "관리자 가게 형태 등록")
    @PostMapping("/form/{id}")
    public void postForm(
            @PathVariable Long id,
            @RequestBody List<@Valid AdminStoreFormCreateRequest> adminStoreFormCreateRequestList) {
        storeSpaceService.save(id, adminStoreFormCreateRequestList);
    }
}
