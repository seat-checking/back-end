package project.seatsence.src.store.dto;

import project.seatsence.src.store.domain.CustomReservationField;
import project.seatsence.src.store.domain.StoreMember;
import project.seatsence.src.store.dto.response.StoreCustomReservationFieldListResponse;
import project.seatsence.src.store.dto.response.StoreMemberListResponse;

public class StoreCustomReservationFieldMapper {
    public static StoreCustomReservationFieldListResponse.CustomReservationFieldResponse toCustomReservationFieldResponse(
            CustomReservationField customReservationField) {
        return StoreCustomReservationFieldListResponse.CustomReservationFieldResponse.builder()
                .title(customReservationField.getTitle())
                .type(customReservationField.getType())
                .contentGuide(customReservationField.getContentGuide())
                .build();
    }
}
