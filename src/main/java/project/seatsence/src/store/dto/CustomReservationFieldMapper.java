package project.seatsence.src.store.dto;

import project.seatsence.src.store.domain.CustomReservationField;
import project.seatsence.src.store.dto.response.StoreCustomReservationFieldListResponse;

public class CustomReservationFieldMapper {
    public static StoreCustomReservationFieldListResponse.CustomReservationFieldResponse
            toCustomReservationFieldResponse(CustomReservationField customReservationField) {
        return StoreCustomReservationFieldListResponse.CustomReservationFieldResponse.builder()
                .id(customReservationField.getId())
                .title(customReservationField.getTitle())
                .type(customReservationField.getType())
                .contentGuide(customReservationField.getContentGuide())
                .build();
    }
}
