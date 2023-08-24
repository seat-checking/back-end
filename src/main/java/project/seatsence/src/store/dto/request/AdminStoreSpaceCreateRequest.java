package project.seatsence.src.store.dto.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@NoArgsConstructor
@Validated
public class AdminStoreSpaceCreateRequest {

    @NotBlank(message = "스페이스의 이름을 입력해주세요.")
    private String storeSpaceName;

    @Positive(message = "스페이스의 높이를 입력해주세요.")
    private int height;

    @NotNull(message = "예약 단위를 선택해주세요.")
    private ReservationUnitRequest reservationUnit;

    @Valid private List<AdminStoreSpaceTableRequest> tableList;

    @Valid private List<AdminStoreSpaceChairRequest> chairList;
}
