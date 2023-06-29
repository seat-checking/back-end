package project.seatsence.src.store.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.dto.StoreMapper;
import project.seatsence.src.store.dto.response.StoreDetailResponse;
import project.seatsence.src.store.service.StoreService;

@RequestMapping("/v1/users/stores")
@RestController
@RequiredArgsConstructor
@Tag(name = "04. [Store - User]")
public class StoreApi {

    private final StoreService storeService;
    private final StoreMapper storeMapper;

    @Operation(summary = "사용자 가게 정보 가져오기")
    @GetMapping("/{id}")
    public StoreDetailResponse getStore(@PathVariable Long id) {
        Store store = storeService.findById(id);
        return storeMapper.toDto(store);
    }
}
