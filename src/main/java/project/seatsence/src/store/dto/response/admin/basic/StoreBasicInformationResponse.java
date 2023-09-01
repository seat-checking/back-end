package project.seatsence.src.store.dto.response.admin.basic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.seatsence.src.store.domain.Store;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class StoreBasicInformationResponse {
    private String storeName;
    private String address;
    private String detailAddress;
    private String category;
    private String mainImage; // TODO 메인 이미지 및 이미지 리스트 전송
    private String introduction;

    public static StoreBasicInformationResponse of(Store store) {
        return StoreBasicInformationResponse.builder()
                .storeName(store.getStoreName())
                .address(store.getAddress())
                .detailAddress(store.getDetailAddress())
                .category(store.getCategory() == null ? null : store.getCategory().getValue())
                .mainImage(store.getMainImage())
                .introduction(store.getIntroduction())
                .build();
    }
}
