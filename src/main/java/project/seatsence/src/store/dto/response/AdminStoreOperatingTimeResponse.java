package project.seatsence.src.store.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.seatsence.global.utils.EnumUtils;
import project.seatsence.src.store.domain.Day;
import project.seatsence.src.store.domain.Store;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class AdminStoreOperatingTimeResponse {

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

    public static AdminStoreOperatingTimeResponse of(Store store) {
        return AdminStoreOperatingTimeResponse.builder()
                .dayOff(EnumUtils.getEnumListFromString(store.getDayOff(), Day.class))
                .monOpenTime(store.getMonOpenTime())
                .monCloseTime(store.getMonCloseTime())
                .tueOpenTime(store.getTueOpenTime())
                .tueCloseTime(store.getTueCloseTime())
                .wedOpenTime(store.getWedOpenTime())
                .wedCloseTime(store.getWedCloseTime())
                .thuOpenTime(store.getThuOpenTime())
                .thuCloseTime(store.getThuCloseTime())
                .friOpenTime(store.getFriOpenTime())
                .friCloseTime(store.getFriCloseTime())
                .satOpenTime(store.getSatOpenTime())
                .satCloseTime(store.getSatCloseTime())
                .sunOpenTime(store.getSunOpenTime())
                .sunCloseTime(store.getSunCloseTime())
                .breakTime(store.getBreakTime())
                .useTimeLimit(store.getUseTimeLimit())
                .build();
    }
}
