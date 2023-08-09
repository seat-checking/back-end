package project.seatsence.src.utilization.dto.reservation.response;

import io.swagger.v3.oas.annotations.Parameter;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import project.seatsence.src.utilization.domain.reservation.Reservation;

@Getter
@Builder
public class UserReservationListResponse {
    @Parameter(name = "예약 식별자", description = "예약 id", example = "1")
    private Long reservationId;

    @Parameter(name = "가게명", description = "예약한 가게 이름", example = "Catch cafe")
    private String storeName;

    @Parameter(
            name = "유저가 예약한 예약단위",
            description = "유저가 좌석을 예약했는지, 스페이스를 예약했는지에 대한 정보",
            example = "좌석")
    private String reservationUnitReservedByUser;

    @Parameter(name = "스페이스명", description = "예약한 가게의 스페이스명", example = "Catch cafe")
    private String storeSpaceName;

    private String reservedPlace; // 유저가 예약한 좌석의 관리 id나 유저가 예약한 스페이스 이름

    @Parameter(name = "예약 시작 일정", description = "예약 시작 날짜와 시간", example = "2023-08-07T10:30:00.000")
    private LocalDateTime reservationStartDateAndTime;

    @Parameter(name = "예약 끝 일정", description = "예약 끝 날짜와 시간", example = "2023-08-07T11:30:00.000")
    private LocalDateTime reservationEndDateAndTime;

    private String storeMainImage;

    public static UserReservationListResponse from(Reservation reservation) {
        String reservationUnitReservedByUser = null;
        String reservedPlace = null;

        if (reservation.getStoreChair() == null) {
            reservationUnitReservedByUser = "스페이스";
            reservedPlace = reservation.getStoreSpace().getName();
        } else if (reservation.getStoreSpace() == null) {
            reservationUnitReservedByUser = "좌석";
            reservedPlace = reservation.getStoreChair().getManageId();
        }

        return UserReservationListResponse.builder()
                .reservationId(reservation.getId())
                .storeName(reservation.getStore().getName())
                .reservationUnitReservedByUser(reservationUnitReservedByUser)
                .storeSpaceName(reservation.getStoreSpace().getName())
                .reservedPlace(reservedPlace)
                .reservationStartDateAndTime(reservation.getReservationStartDateAndTime())
                .reservationEndDateAndTime(reservation.getReservationEndDateAndTime())
                .storeMainImage(reservation.getStore().getMainImage())
                .build();
    }
}
