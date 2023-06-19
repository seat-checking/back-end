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
    @NotNull
    private Long id;

    // TODO user 연결하기

    @NotBlank private String name;

    @NotBlank private String introduction;

    @NotBlank private String location;

    private String mainImage;

    @NotNull private int totalFloor;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Category category;

    private String dayOff;

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

    @NotNull private int avgUseTime;
}
