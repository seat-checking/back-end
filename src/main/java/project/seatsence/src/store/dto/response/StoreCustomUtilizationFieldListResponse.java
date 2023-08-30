package project.seatsence.src.store.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.seatsence.src.store.domain.CustomUtilizationFieldType;

@Getter
@AllArgsConstructor
@Builder
public class StoreCustomUtilizationFieldListResponse {

    private List<CustomUtilizationFieldResponse> StoreCustomUtilizationFieldList;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class CustomUtilizationFieldResponse {
        private Long id;
        private String title;
        private CustomUtilizationFieldType type;
        private String contentGuide;
    }
}
