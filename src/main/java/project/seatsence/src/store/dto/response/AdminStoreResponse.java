package project.seatsence.src.store.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import lombok.*;
import project.seatsence.global.entity.BaseTimeAndStateEntity;
import project.seatsence.src.store.domain.Category;
import project.seatsence.src.store.domain.Day;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminStoreResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // TODO user 연결하기

    private List<String> wifiList;
    private String storeName;
    private String introduction;
    private String location;

    // TODO 대표 이미지 업로드 설정(필수)

    private String mainImage;
    private Category category;
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
    private int avgUseTime;
    private String createdBy;
    private String lastModifiedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BaseTimeAndStateEntity.State state;

    //    public static AdminStoreResponse of(Store store) {
    //        return AdminStoreResponse.builder()
    //                .id(store.getId())
    //
    // .wifiList(store.getWifiList().stream().map(StoreWifi::getWifi).collect(Collectors.toList()))
    //                .storeName(store.getStoreName())
    //                .location(store.getLocation())
    //                .introduction(store.getIntroduction())
    //                .mainImage(store.getMainImage())
    //                .category(store.getCategory())
    //                .dayOff(EnumUtils.getEnumListFromString(store.getDayOff(), Day.class))
    //                .monOpenTime(store.getMonOpenTime())
    //                .monCloseTime(store.getMonCloseTime())
    //                .tueOpenTime(store.getTueOpenTime())
    //                .tueCloseTime(store.getTueCloseTime())
    //                .wedOpenTime(store.getWedOpenTime())
    //                .wedCloseTime(store.getWedCloseTime())
    //                .thuOpenTime(store.getThuOpenTime())
    //                .thuCloseTime(store.getThuCloseTime())
    //                .friOpenTime(store.getFriOpenTime())
    //                .friCloseTime(store.getFriCloseTime())
    //                .satOpenTime(store.getSatOpenTime())
    //                .satCloseTime(store.getSatCloseTime())
    //                .sunOpenTime(store.getSunOpenTime())
    //                .sunCloseTime(store.getSunCloseTime())
    //                .breakTime(store.getBreakTime())
    //                .useTimeLimit(store.getUseTimeLimit())
    //                .avgUseTime(store.getAvgUseTime())
    //                .createdBy(store.getCreatedBy())
    //                .lastModifiedBy(store.getLastModifiedBy())
    //                .createdAt(store.getCreatedAt())
    //                .updatedAt(store.getUpdatedAt())
    //                .state(store.getState())
    //                .build();
    //
    //    }
}
