package project.seatsence.src.store.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.*;
import project.seatsence.global.entity.BaseEntity;
import project.seatsence.global.utils.EnumUtils;
import project.seatsence.src.store.dto.request.AdminStoreBasicInformationRequest;
import project.seatsence.src.store.dto.request.AdminStoreOperatingTimeRequest;
import project.seatsence.src.user.domain.User;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreWifi> wifiList = new ArrayList<>();

    @NotBlank private String storeName;

    private String introduction;

    private String address;
    private String detailAddress;

    // TODO 대표 이미지 업로드 설정(필수값)

    private String mainImage;

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
            String storeName,
            String address,
            String detailAddress) {
        this.user = user;
        this.businessRegistrationNumber = businessRegistrationNumber;
        this.openDate = openDate;
        this.adminName = adminName;
        this.storeName = storeName;
        this.address = address;
        this.detailAddress = detailAddress;
    }

    public void updateBasicInformation(AdminStoreBasicInformationRequest request) {
        this.storeName = request.getStoreName();
        this.address = request.getAddress();
        this.detailAddress = request.getDetailAddress();
        this.category = EnumUtils.getEnumFromString(request.getCategory(), Category.class);
        this.mainImage = request.getMainImage();
        this.introduction = request.getIntroduction();
    }

    public void updateOperatingTime(AdminStoreOperatingTimeRequest request) {
        this.dayOff = EnumUtils.getStringFromEnumList(request.getDayOff());
        this.monOpenTime = request.getMonOpenTime();
        this.monCloseTime = request.getMonCloseTime();
        this.tueOpenTime = request.getTueOpenTime();
        this.tueCloseTime = request.getTueCloseTime();
        this.wedOpenTime = request.getWedOpenTime();
        this.wedCloseTime = request.getWedCloseTime();
        this.thuOpenTime = request.getThuOpenTime();
        this.thuCloseTime = request.getThuCloseTime();
        this.friOpenTime = request.getFriOpenTime();
        this.friCloseTime = request.getFriCloseTime();
        this.satOpenTime = request.getSatOpenTime();
        this.satCloseTime = request.getSatCloseTime();
        this.sunOpenTime = request.getSunOpenTime();
        this.sunCloseTime = request.getSunCloseTime();
        this.breakTime = request.getBreakTime();
        this.useTimeLimit = request.getUseTimeLimit();
    }

    public void delete() {
        this.state = State.INACTIVE;
    }
}
