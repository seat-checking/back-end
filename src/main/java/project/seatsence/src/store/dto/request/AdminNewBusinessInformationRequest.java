package project.seatsence.src.store.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import project.seatsence.global.annotation.ValidBusinessRegistrationNumber;

@Data
@Getter
public class AdminNewBusinessInformationRequest {

    @ValidBusinessRegistrationNumber
    @NotBlank(message = "사업자등록번호가 입력되지 않았습니다.")
    private String businessRegistrationNumber;

    @NotBlank(message = "개업일자가 입력되지 않았습니다.")
    private String openDate;

    @NotBlank(message = "대표자명이 입력되지 않았습니다.")
    private String adminName;

    @NotBlank(message = "가게명이 입력되지 않았습니다.")
    private String storeName;
}
