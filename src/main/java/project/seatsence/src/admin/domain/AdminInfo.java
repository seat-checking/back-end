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
    @Column(name = "id", nullable = false)
    private Long adminInfoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "employer_id_number", nullable = false)
    private String employerIdNumber;

    @Column(name = "open_date", nullable = false)
    private LocalDate openDate;

    @Column(name = "admin_name", nullable = false)
    private String adminName;

    @Builder
    public AdminInfo(User user, String employerIdNumber, LocalDate openDate, String adminName) {
        this.user = user;
        this.employerIdNumber = employerIdNumber;
        this.openDate = openDate;
        this.adminName = adminName;
    }
}
