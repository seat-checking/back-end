package project.seatsence.src.store.domain;

import javax.persistence.*;
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
    @Column(name = "id", nullable = false)
    private Long id;

    // TODO user 연결하기

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "introduction", nullable = false)
    private String introduction;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "main_image", nullable = true)
    private String mainImage;

    @Column(name = "total_floor", nullable = false)
    private int totalFloor;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "day_off", nullable = true)
    private String dayOff;

    @Column(name = "mon_business_hours", nullable = true)
    private String monBusinessHours;

    @Column(name = "tue_business_hours", nullable = true)
    private String tueBusinessHours;

    @Column(name = "wed_business_hours", nullable = true)
    private String wedBusinessHours;

    @Column(name = "thu_business_hours", nullable = true)
    private String thuBusinessHours;

    @Column(name = "fri_business_hours", nullable = true)
    private String friBusinessHours;

    @Column(name = "sat_business_hours", nullable = true)
    private String satBusinessHours;

    @Column(name = "sun_business_hours", nullable = true)
    private String sunBusinessHours;

    @Column(name = "break_time", nullable = true)
    private String breakTime;

    @Column(name = "use_time_limit", nullable = true)
    private String useTimeLimit;

    @Column(name = "memo", nullable = true)
    private String memo;

    @Column(name = "avg_use_time", nullable = false)
    private int avgUseTime;
}
