package project.seatsence.src.store.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.*;
import project.seatsence.global.entity.BaseEntity;
import project.seatsence.src.user.domain.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "store")
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String businessRegistrationNumber;

    @Column(nullable = false)
    private LocalDate openDate;

    @Column(nullable = false)
    private String adminName;

    @OneToMany(mappedBy = "tempStore", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreWifi> wifiList = new ArrayList<>();

    @NotBlank private String storeName;

    private String introduction;

    private String location;

    // TODO 대표 이미지 업로드 설정(필수값)

    private String mainImage;

    private int totalFloor;

    @Enumerated(EnumType.STRING)
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
    private int avgUseTime;

    @JsonIgnore
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreMember> memberList = new ArrayList<>();

    @Builder
    public Store(
            User user,
            String businessRegistrationNumber,
            LocalDate openDate,
            String adminName,
            String storeName) {
        this.user = user;
        this.businessRegistrationNumber = businessRegistrationNumber;
        this.openDate = openDate;
        this.adminName = adminName;
        this.storeName = storeName;
    }
}
