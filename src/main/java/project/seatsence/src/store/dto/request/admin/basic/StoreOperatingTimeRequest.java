package project.seatsence.src.store.dto.request.admin.basic;

import java.util.List;
import lombok.Getter;
import project.seatsence.src.store.domain.Day;

@Getter
public class StoreOperatingTimeRequest {

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
