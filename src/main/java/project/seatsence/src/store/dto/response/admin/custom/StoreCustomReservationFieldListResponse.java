package project.seatsence.src.store.dto.response.admin.custom;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.seatsence.src.store.domain.CustomReservationFieldType;

@Getter
@AllArgsConstructor
@Builder
public class StoreCustomReservationFieldListResponse {

    private List<CustomReservationFieldResponse> StoreCustomReservationFieldList;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class CustomReservationFieldResponse {
        private Long id;
        private String title;
        private CustomReservationFieldType type;
        private String contentGuide;
    }
}
