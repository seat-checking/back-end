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
import project.seatsence.src.store.domain.CustomReservationField;
import project.seatsence.src.store.dto.mapper.CustomReservationFieldMapper;
import project.seatsence.src.store.dto.request.admin.custom.StoreCustomReservationFieldRequest;
import project.seatsence.src.store.dto.response.admin.custom.StoreCustomReservationFieldListResponse;
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
    @PostMapping("/custom-reservation-field/{store-id}")
    public void postStoreCustomReservationField(
            @PathVariable("store-id") Long storeId,
            @Valid @RequestBody
                    StoreCustomReservationFieldRequest storeCustomReservationFieldRequest)
            throws JsonProcessingException {
        storeCustomService.postStoreCustomReservationField(
                storeId, storeCustomReservationFieldRequest);
    }

    @Operation(summary = "가게 커스텀 정보 항목 리스트")
    @GetMapping("/custom-reservation-field/{store-id}")
    public StoreCustomReservationFieldListResponse getStoreCustomReservationField(
            @PathVariable("store-id") Long storeId) {

        List<CustomReservationField> customReservationFields =
                storeCustomService.findAllByStoreIdAndState(storeId);

        List<StoreCustomReservationFieldListResponse.CustomReservationFieldResponse>
                customReservationFieldResponseList =
                        customReservationFields.stream()
                                .map(CustomReservationFieldMapper::toCustomReservationFieldResponse)
                                .collect(Collectors.toList());

        return StoreCustomReservationFieldListResponse.builder()
                .StoreCustomReservationFieldList(customReservationFieldResponseList)
                .build();
    }

    @Operation(summary = "가게 커스텀 정보 항목 수정", description = "타입 단위는 '자유 입력', '선택지 제공' 중 하나로 선택")
    @PatchMapping("/custom-reservation-field/{store-id}")
    public void updateStoreCustomReservationField(
            @PathVariable("store-id") Long storeId,
            @Valid @RequestParam("custom-id") Long customReservationFieldId,
            @Valid @RequestBody
                    StoreCustomReservationFieldRequest storeCustomReservationFieldRequest)
            throws JsonProcessingException {
        storeCustomService.update(
                storeId, customReservationFieldId, storeCustomReservationFieldRequest);
    }

    @Operation(summary = "가게 커스텀 정보 항목 삭제")
    @DeleteMapping("/custom-reservation-field/{store-id}")
    public void deleteStoreCustomReservationField(
            @PathVariable("store-id") Long storeId,
            @Valid @RequestParam("custom-id") Long customReservationFieldId) {
        storeCustomService.delete(customReservationFieldId);
    }
}
