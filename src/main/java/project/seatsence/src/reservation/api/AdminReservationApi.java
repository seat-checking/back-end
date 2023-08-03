package project.seatsence.src.reservation.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.seatsence.src.reservation.service.AdminReservationService;

@RestController
@RequestMapping("/v1/reservations/admins")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "06. [Reservation - Admin]")
public class AdminReservationApi {

    private final AdminReservationService adminReservationService;

    @Operation(summary = "admin 예약 승인")
    @PostMapping("/{store-id}/approve/")
    public void reservationApprove(
            @PathVariable("store-id") Long storeId,
            @RequestParam("reservation-id") Long reservationId) {
        adminReservationService.reservationApprove(reservationId);
    }

    @Operation(summary = "admin 예약 거절")
    @PostMapping("/{store-id}/reject/")
    public void reservationReject(
            @PathVariable("store-id") Long storeId,
            @RequestParam("reservation-id") Long reservationId) {
        adminReservationService.reservationReject(reservationId);
    }
}
