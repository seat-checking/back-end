package project.seatsence.src.store.dto.request.admin.basic;

import lombok.Getter;

@Getter
public class StoreBasicInformationRequest {
    private String storeName;
    private String address;
    private String detailAddress;
    private String category;
    private String introduction;

    public static StoreBasicInformationRequest createAdminStoreBasicInformationRequest(
            String storeName,
            String address,
            String detailAddress,
            String category,
            String introduction) {
        StoreBasicInformationRequest storeBasicInformationRequest =
                new StoreBasicInformationRequest();
        storeBasicInformationRequest.storeName = storeName;
        storeBasicInformationRequest.address = address;
        storeBasicInformationRequest.detailAddress = detailAddress;
        storeBasicInformationRequest.category = category;
        storeBasicInformationRequest.introduction = introduction;
        return storeBasicInformationRequest;
    }
}
