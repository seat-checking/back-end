package project.seatsence.src.store.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import project.seatsence.global.entity.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "store")
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO user 연결하기

    @NotBlank private String name;

    @NotBlank private String introduction;

    @NotBlank private String location;

    // TODO 대표 이미지 업로드 설정(필수)
    private String mainImage;

    @NotNull private int totalFloor;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Category category;

    private String dayOff;
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
    private String memo;
    @NotNull private int avgUseTime;
}
