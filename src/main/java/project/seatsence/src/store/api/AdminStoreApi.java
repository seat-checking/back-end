package project.seatsence.src.store.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.seatsence.src.store.dto.request.AdminStoreCreateRequest;
import project.seatsence.src.store.dto.request.AdminStoreUpdateRequest;
import project.seatsence.src.store.dto.response.AdminStoreResponse;
import project.seatsence.src.store.service.StoreService;

@RequestMapping("/v1/admin/store")
@RestController
@RequiredArgsConstructor
@Tag(name = "03. [Store - Admin]")
@Slf4j
public class AdminStoreApi {

    private final StoreService storeService;

    @Operation(summary = "admin 가게 정보 가져오기")
    @GetMapping("/{id}")
    public AdminStoreResponse getStore(@PathVariable Long id) {
        return storeService.findById(id);
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
}
