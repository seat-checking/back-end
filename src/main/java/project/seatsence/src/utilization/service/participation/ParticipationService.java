package project.seatsence.src.utilization.service.participation;

import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.ACTIVE;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.response.SliceResponse;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.service.UserService;
import project.seatsence.src.utilization.dao.participation.ParticipationRepository;
import project.seatsence.src.utilization.domain.Participation.Participation;
import project.seatsence.src.utilization.domain.Participation.ParticipationStatus;
import project.seatsence.src.utilization.domain.reservation.Reservation;
import project.seatsence.src.utilization.domain.walkin.WalkIn;
import project.seatsence.src.utilization.dto.response.participation.ParticipationListResponse;
import project.seatsence.src.utilization.service.reservation.ReservationService;
import project.seatsence.src.utilization.service.walkin.WalkInService;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticipationService {

    private final UserService userService;

    private final ParticipationRepository participationRepository;

    private final ReservationService reservationService;
    private final WalkInService walkInService;

    public Slice<Participation> getUpcomingParticipation(String userEmail, Pageable pageable) {
        User user = userService.findUserByUserEmailAndState(userEmail);

        Slice<Participation> participationSlice =
                findAllByUserEmailAndParticipationStatusAndStateOrderByStartScheduleDesc(
                        userEmail, ParticipationStatus.UPCOMING_PARTICIPATION, pageable);

        return participationSlice;
    }

    Slice<Participation> findAllByUserEmailAndParticipationStatusAndStateOrderByStartScheduleDesc(
            String email, ParticipationStatus participationStatus, Pageable pageable) {
        return participationRepository
                .findAllByUserEmailAndParticipationStatusAndStateOrderByStartScheduleDesc(
                        email, participationStatus, ACTIVE, pageable);
    }

    public Slice<Participation> getParticipated(String userEmail, Pageable pageable) {
        User user = userService.findUserByUserEmailAndState(userEmail);

        Slice<Participation> participationSlice =
                findAllByUserEmailAndParticipationStatusAndStateOrderByStartScheduleDesc(
                        userEmail, ParticipationStatus.PARTICIPATED, pageable);

        return participationSlice;
    }

    public SliceResponse<ParticipationListResponse.ParticipationResponse> toSliceResponse(
            Slice<Participation> participationSlice) {
        return SliceResponse.of(participationSlice.map(this::toParticipationResponse));
    }

    private ParticipationListResponse.ParticipationResponse toParticipationResponse(
            Participation participation) {

        String participationPlace = null;
        String storeSpaceName = null;
        LocalDateTime endSchedule = null;

        if (participation.getReservation() != null) { // 예약
            Reservation reservation =
                    reservationService.findByIdAndState(participation.getReservation().getId());
            storeSpaceName = reservation.getReservedStoreSpace().getName();
            endSchedule = reservation.getEndSchedule();
            System.out.println("예약");
        } else { // 바로사용
            WalkIn walkIn = walkInService.findByIdAndState(participation.getWalkIn().getId());
            storeSpaceName = walkIn.getUsedStoreSpace().getName();
            endSchedule = walkIn.getEndSchedule();
            System.out.println("바로사용");
        }

        return ParticipationListResponse.ParticipationResponse.builder()
                .id(participation.getId())
                .storeName(participation.getStore().getStoreName())
                .storeSpaceName(storeSpaceName)
                .startSchedule(participation.getStartSchedule())
                .endSchedule(endSchedule)
                .createdAt(participation.getCreatedAt())
                .storeMainImage(participation.getStore().getMainImage())
                .userNickname(participation.getUser().getNickname())
                .build();
    }
}
