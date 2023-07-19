package project.seatsence.src.store.domain;

import javax.persistence.*;
import lombok.*;
import project.seatsence.global.entity.BaseEntity;
import project.seatsence.src.admin.domain.AdminInfo;
import project.seatsence.src.user.domain.User;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "store_member")
public class StoreMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_info_id")
    private AdminInfo adminInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StorePosition position;

    @Column(nullable = false)
    private String permissionByMenu;

    @Builder
    public StoreMember(
            AdminInfo adminInfo,
            User user,
            Store store,
            StorePosition position,
            String permissionByMenu) {
        this.adminInfo = adminInfo;
        this.user = user;
        this.store = store;
        this.position = position;
        this.permissionByMenu = permissionByMenu;
    }
}
