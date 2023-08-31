package project.seatsence.src.store.dto.mapper;

import project.seatsence.src.store.domain.CustomUtilizationField;
import project.seatsence.src.store.dto.response.admin.custom.StoreCustomUtilizationFieldListResponse;

public class CustomUtilizationFieldMapper {
    public static StoreCustomUtilizationFieldListResponse.CustomUtilizationFieldResponse
    toCustomUtilizationFieldResponse(CustomUtilizationField customUtilizationField) {
        return StoreCustomUtilizationFieldListResponse.CustomUtilizationFieldResponse.builder()
                .id(customUtilizationField.getId())
                .title(customUtilizationField.getTitle())
                .type(customUtilizationField.getType())
                .contentGuide(customUtilizationField.getContentGuide())
                .build();
    }
}
