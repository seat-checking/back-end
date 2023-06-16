package project.seatsence.src.store.dto.response;

import lombok.Getter;
import lombok.Setter;
import project.seatsence.src.store.domain.Category;
import project.seatsence.src.store.domain.Day;

import java.util.List;

@Getter
@Setter
public class AdminStoreResponse {

    private Long id;
    private String name;
    private String introduction;
    private String location;
    private String mainImage;
    private int totalFloor;
    private Category category;
    private List<Day> dayOff;
    private String monBusinessHours;
    private String tueBusinessHours;
    private String wedBusinessHours;
    private String thuBusinessHours;
    private String friBusinessHours;
    private String satBusinessHours;
    private String sunBusinessHours;
    private String breakTime;
    private String useTimeLimit;
    private String memo;
    private int avgUseTime;
    private String createdBy;
    private String lastModifiedBy;
}
