package project.seatsence.src.store.dto.request;

import lombok.Getter;

@Getter
public class AdminStoreBasicInformationRequest {
    private String storeName;
    private String address;
    private String detailAddress;

    private String category;
    private String mainImage; // TODO 메인 이미지 및 이미지 리스트 전송
    private String introduction;
}
