package project.seatsence.src.admin.dto.request;

import lombok.Data;
import lombok.Getter;
import project.seatsence.global.annotation.ValidEmployerIdNumber;

import javax.validation.constraints.NotBlank;

@Data
@Getter
public class AdminNewBusinessRegistrationNumberRequest {

    @ValidEmployerIdNumber(message = "사업자등록번호는 10자리의 숫자입니다.")
    @NotBlank(message = "사업자등록번호가 입력되지 않았습니다.")
    private String businessRegistrationNumber;

    @NotBlank(message = "오픈일자가 입력되지 않았습니다.")
    private String openDate;

    @NotBlank(message = "대표자 이름이 입력되지 않았습니다.")
    private String adminName;
}
