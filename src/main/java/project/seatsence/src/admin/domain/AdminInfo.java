package project.seatsence.src.admin.domain;

import java.time.LocalDate;
import javax.persistence.*;
import lombok.*;
import project.seatsence.global.entity.BaseEntity;
import project.seatsence.src.user.domain.User;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "admin_info")
public class AdminInfo extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long adminInfoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String employerIdNumber;

    @Column(nullable = false)
    private LocalDate openDate;

    @Column(nullable = false)
    private String adminName;

    @Builder
    public AdminInfo(User user, String employerIdNumber, LocalDate openDate, String adminName) {
        this.user = user;
        this.employerIdNumber = employerIdNumber;
        this.openDate = openDate;
        this.adminName = adminName;
    }
}
