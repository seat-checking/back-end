package project.seatsence.src.store.api.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.seatsence.src.store.domain.CustomUtilizationField;
import project.seatsence.src.store.dto.request.admin.custom.StoreCustomUtilizationFieldRequest;
import project.seatsence.src.store.dto.response.admin.custom.StoreCustomUtilizationFieldListResponse;
import project.seatsence.src.store.service.StoreCustomService;

@RequestMapping("/v1/stores/admins")
@RestController
@RequiredArgsConstructor
@Tag(name = "03 - 4. [Store - Admin Custom]")
@Slf4j
@Validated
public class StoreCustomApi {

    private final StoreCustomService storeCustomService;

    @Operation(summary = "가게 커스텀 정보 항목 입력", description = "타입 단위는 '자유 입력', '선택지 제공' 중 하나로 선택")
    @PostMapping("/custom-utilization-field/{store-id}")
    public void postStoreCustomUtilizationField(
            @PathVariable("store-id") Long storeId,
            @Valid @RequestBody
                    StoreCustomUtilizationFieldRequest storeCustomUtilizationFieldRequest)
            throws JsonProcessingException {
        storeCustomService.postStoreCustomUtilizationField(
                storeId, storeCustomUtilizationFieldRequest);
    }

    @Operation(summary = "가게 커스텀 정보 항목 리스트")
    @GetMapping("/custom-utilization-field/{store-id}")
    public StoreCustomUtilizationFieldListResponse getStoreCustomUtilizationField(
            @PathVariable("store-id") Long storeId) {

        List<CustomUtilizationField> customUtilizationFields =
                storeCustomService.findAllByStoreIdAndState(storeId);

        List<StoreCustomUtilizationFieldListResponse.CustomUtilizationFieldResponse>
                customUtilizationFieldResponseList =
                        storeCustomService.toCustomUtilizationFieldResponseList(
                                customUtilizationFields);

        return StoreCustomUtilizationFieldListResponse.builder()
                .StoreCustomUtilizationFieldList(customUtilizationFieldResponseList)
                .build();
    }

    @Operation(summary = "가게 커스텀 정보 항목 수정", description = "타입 단위는 '자유 입력', '선택지 제공' 중 하나로 선택")
    @PatchMapping("/custom-utilization-field/{store-id}")
    public void updateStoreCustomUtilizationField(
            @PathVariable("store-id") Long storeId,
            @Valid @RequestParam("custom-id") Long customUtilizationFieldId,
            @Valid @RequestBody
                    StoreCustomUtilizationFieldRequest storeCustomUtilizationFieldRequest)
            throws JsonProcessingException {
        storeCustomService.update(
                storeId, customUtilizationFieldId, storeCustomUtilizationFieldRequest);
    }

    @Operation(summary = "가게 커스텀 정보 항목 삭제")
    @DeleteMapping("/custom-utilization-field/{store-id}")
    public void deleteStoreCustomUtilizationField(
            @PathVariable("store-id") Long storeId,
            @Valid @RequestParam("custom-id") Long customUtilizationFieldId) {
        storeCustomService.delete(customUtilizationFieldId);
    }
}
