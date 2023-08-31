package project.seatsence.src.store.dto.request;

import lombok.Getter;

@Getter
public class AdminStoreBasicInformationRequest {
    private String storeName;
    private String address;
    private String detailAddress;
    private String category;
    private String introduction;

    public static AdminStoreBasicInformationRequest createAdminStoreBasicInformationRequest(
            String storeName,
            String address,
            String detailAddress,
            String category,
            String introduction) {
        AdminStoreBasicInformationRequest adminStoreBasicInformationRequest =
                new AdminStoreBasicInformationRequest();
        adminStoreBasicInformationRequest.storeName = storeName;
        adminStoreBasicInformationRequest.address = address;
        adminStoreBasicInformationRequest.detailAddress = detailAddress;
        adminStoreBasicInformationRequest.category = category;
        adminStoreBasicInformationRequest.introduction = introduction;
        return adminStoreBasicInformationRequest;
    }
}
