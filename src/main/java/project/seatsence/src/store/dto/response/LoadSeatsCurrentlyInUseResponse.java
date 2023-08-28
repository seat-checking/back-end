package project.seatsence.src.store.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoadSeatsCurrentlyInUseResponse {
    private List<chairCurrentlyInUse> AllChairCurrentlyInUse;

    @Getter
    @AllArgsConstructor
    @Builder
    public class chairCurrentlyInUse {
        private Long id;
    }
}
