package project.seatsence.src.store.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.seatsence.src.store.dto.response.AdminStoreResponse;
import project.seatsence.src.store.service.StoreService;

@RequestMapping("/v1/admin/store")
@RestController
@RequiredArgsConstructor
@Tag(name = "[Store - Admin]")
@Slf4j
public class AdminStoreApi {

    private final StoreService storeService;

    @Operation(summary = "admin 가게 정보 가져오기")
    @GetMapping("/{id}")
    public ResponseEntity<AdminStoreResponse> getStore(@PathVariable Long id) {
        AdminStoreResponse adminStoreResponse = storeService.findById(id);
        return new ResponseEntity<>(adminStoreResponse, HttpStatus.OK);
    }
}
