package project.seatsence.src.store.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoadSeatsCurrentlyInUseResponse {

    // 스페이스단위 이용 : 스페이스의 모든 의자를 리스트로 넘기지 않고, 해당 필드 true / 의자단위 이용 : 해당 필드 false
    private Boolean isThisSpaceCurrentlyInUse;

    private List<chairCurrentlyInUse> AllChairsCurrentlyInUse;

    @Getter
    @AllArgsConstructor
    @Builder
    public class chairCurrentlyInUse {
        private Long id;
    }
}
