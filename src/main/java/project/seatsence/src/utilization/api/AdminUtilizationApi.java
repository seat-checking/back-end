package project.seatsence.src.utilization.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.seatsence.src.utilization.service.AdminUtilizationService;

@RestController
@RequestMapping("/v1/utilization/admins")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "09. [Utilization - Admin]")
public class AdminUtilizationApi {

    private final AdminUtilizationService adminUtilizationService;

    @Operation(summary = "admin 이용 강제 퇴실")
    @PostMapping("/forced_check_out/")
    public void utilizationForcedCheckOut(@RequestParam("utilization-id") Long utilizationId) {
        adminUtilizationService.forceCheckOut(utilizationId);
    }
}
