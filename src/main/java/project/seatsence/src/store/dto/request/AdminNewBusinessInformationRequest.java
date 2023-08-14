package project.seatsence.src.store.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import project.seatsence.global.annotation.ValidBusinessRegistrationNumber;

@Data
@Getter
public class AdminNewBusinessInformationRequest {

    @NotBlank(message = "가게명이 입력되지 않았습니다.")
    private String storeName;

    @NotBlank(message = "가게 위치를 입력해주세요.")
    private String address;

    @NotBlank(message = "가게 상세 위치를 입력해주세요.")
    private String detailAddress;

    @ValidBusinessRegistrationNumber
    @NotBlank(message = "사업자등록번호가 입력되지 않았습니다.")
    private String businessRegistrationNumber;

    @NotBlank(message = "개업일자가 입력되지 않았습니다.")
    private String openDate;

    @NotBlank(message = "대표자명이 입력되지 않았습니다.")
    private String adminName;
}
