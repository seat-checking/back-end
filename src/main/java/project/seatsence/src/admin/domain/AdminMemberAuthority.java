package project.seatsence.src.admin.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.user.domain.User;

@Entity
@Getter
@Setter
@Table(name = "admin_member_authority")
public class AdminMemberAuthority {

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
    private AdminAuthority authority;

    @Column(nullable = false)
    private String permissionByMenu;
}
