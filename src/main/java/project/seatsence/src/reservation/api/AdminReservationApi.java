package project.seatsence.src.reservation.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.seatsence.src.reservation.domain.ReservationStatus;
import project.seatsence.src.reservation.service.AdminReservationService;

@RestController
@RequestMapping("/v1/reservations/admins")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "06. [Reservation - Admin]")
public class AdminReservationApi {

    private final AdminReservationService adminReservationService;

    @Operation(summary = "admin 예약 상태 변경하기", description = "예약 상태는 '승인', '거절' 중 하나로 선택")
    @PostMapping("/{store-id}/")
    public void setReservationStatus(
            @PathVariable("store-id") Long storeId,
            @RequestParam("reservation-id") Long reservationId,
            @RequestBody ReservationStatus reservationStatus) {
        adminReservationService.setReservationStatus(reservationId, reservationStatus);
    }
}
