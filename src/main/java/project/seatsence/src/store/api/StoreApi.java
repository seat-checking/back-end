package project.seatsence.src.store.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.seatsence.src.store.dto.response.LoadSeatStatisticsInformationOfStoreResponse;
import project.seatsence.src.store.dto.response.admin.space.StoreSpaceSeatResponse;
import project.seatsence.src.store.service.StoreService;
import project.seatsence.src.store.service.StoreSpaceService;

@RequestMapping("/v1/stores")
@RestController
@RequiredArgsConstructor
@Tag(name = "05. [Store]")
@Validated
public class StoreApi {
    private final StoreService storeService;
    private final StoreSpaceService storeSpaceService;

    @Operation(summary = "가게 좌석 통계정보 조회")
    @GetMapping("/seats/statistics_information/{store-id}")
    public LoadSeatStatisticsInformationOfStoreResponse loadSeatStatisticsInformationOfStore(
            @Parameter(description = "가게 식별자", in = ParameterIn.PATH, example = "1")
                    @PathVariable("store-id")
                    Long storeId) {
        return storeService.loadSeatStatisticsInformationOfStore(storeId);
    }

    @Operation(summary = "스페이스의 좌석 정보 불러오기")
    @GetMapping("/spaces/seats/{store-space-id}")
    public StoreSpaceSeatResponse getStoreSpaceSeat(
            @PathVariable("store-space-id") Long storeSpaceId) {
        return storeSpaceService.getStoreSpaceSeat(storeSpaceId);
    }



}
