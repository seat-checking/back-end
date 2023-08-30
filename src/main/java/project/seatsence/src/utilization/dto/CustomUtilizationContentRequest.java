package project.seatsence.src.utilization.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class CustomUtilizationContentRequest {

    @NotNull
    private Long fieldId;

    @NotBlank(message = "이용 신청 정보 내용이이 입력지 않았습니다.")
    private String content;
}
