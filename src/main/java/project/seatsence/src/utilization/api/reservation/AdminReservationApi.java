package project.seatsence.src.utilization.api.reservation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.seatsence.src.utilization.domain.reservation.Reservation;
import project.seatsence.src.utilization.dto.reservation.ReservationMapper;
import project.seatsence.src.utilization.dto.reservation.response.AdminReservationListResponse;
import project.seatsence.src.utilization.service.reservation.AdminReservationService;

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

    @Operation(summary = "admin 예약 전체 리스트")
    @GetMapping("/{store-id}/all-list")
    public AdminReservationListResponse entireReservationList(
            @PathVariable("store-id") Long storeId) {

        List<Reservation> reservations = adminReservationService.getAllReservationAndState(storeId);
        List<AdminReservationListResponse.ReservationResponse> reservationResponseList =
                reservations.stream()
                        .map(ReservationMapper::toReservationResponse)
                        .collect(Collectors.toList());

        return AdminReservationListResponse.builder()
                .reservationResponseList(reservationResponseList)
                .build();
    }

    @Operation(summary = "admin 예약 대기 리스트")
    @GetMapping("/{store-id}/pending-list")
    public AdminReservationListResponse pendingReservationList(
            @PathVariable("store-id") Long storeId) {

        List<Reservation> reservations = adminReservationService.getPendingReservation(storeId);
        List<AdminReservationListResponse.ReservationResponse> reservationResponseList =
                reservations.stream()
                        .map(ReservationMapper::toReservationResponse)
                        .collect(Collectors.toList());

        return AdminReservationListResponse.builder()
                .reservationResponseList(reservationResponseList)
                .build();
    }

    @Operation(summary = "admin 예약 처리 완료 리스트")
    @GetMapping("/{store-id}/processed-list")
    public AdminReservationListResponse processedReservationList(
            @PathVariable("store-id") Long storeId) {

        List<Reservation> reservations = adminReservationService.getProcessedReservation(storeId);
        List<AdminReservationListResponse.ReservationResponse> reservationResponseList =
                reservations.stream()
                        .map(ReservationMapper::toReservationResponse)
                        .collect(Collectors.toList());

        return AdminReservationListResponse.builder()
                .reservationResponseList(reservationResponseList)
                .build();
    }
}
