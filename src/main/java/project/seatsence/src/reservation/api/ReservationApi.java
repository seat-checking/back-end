package project.seatsence.src.reservation.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.seatsence.src.reservation.domain.Reservation;
import project.seatsence.src.reservation.dto.request.SeatReservationRequest;
import project.seatsence.src.reservation.service.SeatReservationService;
import project.seatsence.src.store.domain.StoreChair;
import project.seatsence.src.store.service.StoreChairService;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.service.UserService;

@RestController
@RequestMapping("/v1/reservation")
@Tag(name = "05. [reservation]")
@Validated
@RequiredArgsConstructor
public class ReservationApi {
    private final SeatReservationService seatReservationService;
    private final StoreChairService storeChairService;
    private final UserService userService;

    @Operation(summary = "유저 좌석 예약")
    @PostMapping("/seat")
    public void seatReservation(@RequestBody SeatReservationRequest seatReservationRequest) {
        StoreChair storeChairFound =
                storeChairService.findById(seatReservationRequest.getStoreChairId());

        User userFound = userService.findById(seatReservationRequest.getUserId());

        Reservation reservation =
                Reservation.builder()
                        .storeChair(storeChairFound)
                        .storeSpace(null)
                        .user(userFound)
                        .reservationStartDateAndTime(
                                seatReservationRequest.getReservationStartDateAndTime())
                        .reservationEndDateAndTime(
                                seatReservationRequest.getReservationEndDateAndTime())
                        .build();

        seatReservationService.seatReservation(reservation);
    }
}
