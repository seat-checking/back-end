package project.seatsence.src.utilization.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.seatsence.src.utilization.dto.response.LoadSeatsCurrentlyInUseResponse;
import project.seatsence.src.utilization.service.UtilizationService;

@RequestMapping("/v1/utilization")
@RestController
@RequiredArgsConstructor
@Tag(name = "10. [Utilization]")
@Slf4j
@Validated
public class UtilizationApi {
    private UtilizationService utilizationService;

    @Operation(summary = "현재 이용중인 좌석 조회 API", description = "현재 이용중인 좌석(의자, 스페이스)을 조회합니다")
    @GetMapping("/seat/current-in-use/{space-id}")
    public LoadSeatsCurrentlyInUseResponse loadSeatCurrentlyInUse(
            @Parameter(name = "스페이스 식별자", in = ParameterIn.PATH, example = "1")
            @PathVariable("space-id")
            Long spaceId) {
        return utilizationService.loadSeatCurrentlyInUse(spaceId);
    }
}
