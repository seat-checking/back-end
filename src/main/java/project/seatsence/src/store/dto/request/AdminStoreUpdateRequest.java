package project.seatsence.src.store.dto.request;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.seatsence.global.annotation.ValidCategory;
import project.seatsence.global.annotation.ValidWifi;
import project.seatsence.src.store.domain.Day;

@NoArgsConstructor
@Getter
@Setter
public class AdminStoreUpdateRequest {
    @ValidWifi private List<String> wifi;

    @NotBlank(message = "가게 이름을 입력해주세요.")
    private String name;

    // TODO 가게 이미지

    @NotBlank(message = "가게 설명을 입력해주세요.")
    private String introduction;

    @NotBlank(message = "가게 위치를 입력해주세요.")
    private String location;

    @Positive(message = "층수는 1 이상의 수로 입력해주세요.")
    private int totalFloor;

    @NotNull(message = "가게 종류를 선택해주세요.")
    @ValidCategory
    private String category;

    private List<Day> dayOff;
    private String monOpenTime;
    private String monCloseTime;
    private String tueOpenTime;
    private String tueCloseTime;
    private String wedOpenTime;
    private String wedCloseTime;
    private String thuOpenTime;
    private String thuCloseTime;
    private String friOpenTime;
    private String friCloseTime;
    private String satOpenTime;
    private String satCloseTime;
    private String sunOpenTime;
    private String sunCloseTime;
    private String breakTime;
    private String useTimeLimit;
}
