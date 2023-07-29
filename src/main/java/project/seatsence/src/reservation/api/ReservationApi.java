package project.seatsence.src.reservation.api;

import static project.seatsence.global.code.ResponseCode.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.seatsence.global.code.ResponseCode;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.reservation.domain.Reservation;
import project.seatsence.src.reservation.dto.request.SeatReservationRequest;
import project.seatsence.src.reservation.dto.request.SpaceReservationRequest;
import project.seatsence.src.reservation.service.ReservationService;
import project.seatsence.src.store.domain.StoreChair;
import project.seatsence.src.store.domain.StoreSpace;
import project.seatsence.src.store.service.StoreChairService;
import project.seatsence.src.store.service.StoreSpaceService;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.service.UserService;

@RestController
@RequestMapping("/v1/reservations")
@Tag(name = "05. [reservation]")
@Validated
@RequiredArgsConstructor
public class ReservationApi {
    private final ReservationService reservationService;
    private final StoreChairService storeChairService;
    private final StoreSpaceService storeSpaceService;
    private final UserService userService;

    @Operation(summary = "유저 좌석 예약")
    @PostMapping("/seat")
    public void seatReservation(@RequestBody SeatReservationRequest seatReservationRequest) {
        StoreChair storeChairFound =
                storeChairService.findByIdAndState(seatReservationRequest.getStoreChairId());

        if(storeSpaceService.reservationUnitIsSeat(storeChairFound.getStoreSta)) {
            throw new BaseException(INVALID_RESERVATION_UNIT);
        }

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

        reservationService.saveReservation(reservation);
    }

    @Operation(summary = "유저 스페이스 예약")
    @PostMapping("/space")
    public void spaceReservation(@RequestBody SpaceReservationRequest spaceReservationRequest) {
        StoreSpace storeSpaceFound =
                storeSpaceService.findByIdAndState(spaceReservationRequest.getStoreSpaceId());

        if(storeSpaceService.reservationUnitIsSeat(storeSpaceFound)) {
            throw new BaseException(INVALID_RESERVATION_UNIT);
        }

        User userFound = userService.findById(spaceReservationRequest.getUserId());

        Reservation reservation =
                Reservation.builder()
                        .storeChair(null)
                        .storeSpace(storeSpaceFound)
                        .user(userFound)
                        .reservationStartDateAndTime(
                                spaceReservationRequest.getReservationStartDateAndTime())
                        .reservationEndDateAndTime(
                                spaceReservationRequest.getReservationEndDateAndTime())
                        .build();

        reservationService.saveReservation(reservation);
    }
}
