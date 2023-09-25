package project.seatsence.src.utilization.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.seatsence.src.holding.domain.Holding;
import project.seatsence.src.utilization.domain.Utilization;

@Getter
@AllArgsConstructor
@Builder
public class LoadSeatsCurrentlyInUseResponse {

    // 스페이스단위 이용 : 스페이스의 모든 의자를 리스트로 넘기지 않고, 해당 필드 true / 의자단위 이용 : 해당 필드 false
    private Boolean isThisSpaceCurrentlyInUse;

    // 스페이스단위 홀딩 : 스페이스의 모든 의자를 리스트로 넘기지 않고, 해당 필드 true / 의자단위 홀딩 : 해당 필드 false
    private Boolean isThisSpaceCurrentlyHolding;

    private List<ChairCurrentlyInUse> allChairsCurrentlyInUse;
    private List<ChairCurrentlyInUse> allChairsCurrentlyHolding;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ChairCurrentlyInUse {
        private Long id;

        public static ChairCurrentlyInUse from(Utilization utilization) {
            return ChairCurrentlyInUse.builder()
                    .id(utilization.getUsedStoreChair().getId())
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ChairCurrentlyHolding {
        private Long id;

        public static ChairCurrentlyHolding from(Holding holding) {
            Long holdingChairId = null;

            if (holding.getReservation() != null) {
                holdingChairId = holding.getReservation().getReservedStoreChair().getId();
            } else if (holding.getWalkIn() != null) {
                holdingChairId = holding.getWalkIn().getUsedStoreChair().getId();
            }

            return ChairCurrentlyHolding.builder().id(holdingChairId).build();
        }
    }
}
