package project.seatsence.src.reservation.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.seatsence.src.reservation.domain.Reservation;
import project.seatsence.src.reservation.dto.request.SeatReservationRequest;
import project.seatsence.src.reservation.service.SeatReservationService;

@RestController
@RequestMapping("/v1/reservation")
@Tag(name = "05. [reservation]")
@Validated
@RequiredArgsConstructor
public class ReservationApi {
    private final SeatReservationService seatReservationService;

    @Operation(summary = "유저 좌석 예약")
    @PostMapping("/seat")
    public void seatReservation(@RequestBody SeatReservationRequest seatReservationRequest) {
        Reservation.builder().storeChair();
        seatReservationService.seatReservation(seatReservationRequest);
    }
}
