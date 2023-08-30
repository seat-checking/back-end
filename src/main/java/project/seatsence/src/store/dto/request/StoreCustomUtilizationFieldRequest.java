package project.seatsence.src.store.dto.request;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import project.seatsence.src.store.domain.CustomUtilizationFieldType;

@Data
@Getter
public class StoreCustomUtilizationFieldRequest {
    @NotBlank(message = "정보 타이틀이 입력되지 않았습니다.")
    private String title;

    @NotNull(message = "등록할 형식이 선택되지 않았습니다.")
    private CustomUtilizationFieldType type;

    @NotEmpty(message = "정보에 대한 내용 가이드가 입력되지 않았습니다.")
    private List<String> contentGuide;
}
