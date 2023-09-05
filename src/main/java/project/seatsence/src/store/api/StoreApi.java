package project.seatsence.src.store.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.seatsence.src.store.dto.response.LoadSeatStatisticsInformationResponse;
import project.seatsence.src.store.service.StoreService;

@RequestMapping("/v1/stores")
@RestController
@RequiredArgsConstructor
@Tag(name = "03 - 5. [Store]")
@Validated
public class StoreApi {
    private final StoreService storeService;

    @Operation(summary = "가게 좌석 통계정보 조회")
    @GetMapping("/seats/statistics_information/{store-id}")
    public LoadSeatStatisticsInformationResponse loadSeatStatisticsInformation() {}
}
