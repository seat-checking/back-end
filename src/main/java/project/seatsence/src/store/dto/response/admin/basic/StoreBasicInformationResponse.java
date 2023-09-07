package project.seatsence.src.store.dto.response.admin.basic;

import java.util.List;
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
    //    private String mainImage; // TODO 메인 이미지 및 이미지 리스트 전송
    private List<String> storeImages;
    private String introduction;

    public static StoreBasicInformationResponse of(Store store, List<String> storeImages) {
        return StoreBasicInformationResponse.builder()
                .storeName(store.getStoreName())
                .address(store.getAddress())
                .detailAddress(store.getDetailAddress())
                .category(store.getCategory() == null ? null : store.getCategory().getValue())
                .storeImages(storeImages)
                .introduction(store.getIntroduction())
                .build();
    }
}
