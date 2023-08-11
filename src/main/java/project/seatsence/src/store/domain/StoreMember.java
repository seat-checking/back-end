package project.seatsence.src.store.domain;

import javax.persistence.*;
import lombok.*;
import project.seatsence.global.entity.BaseEntity;
import project.seatsence.src.user.domain.User;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "store_member")
public class StoreMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
    public StoreMember(User user, Store store, StorePosition position, String permissionByMenu) {
        this.user = user;
        this.store = store;
        this.position = position;
        this.permissionByMenu = permissionByMenu;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setPermissionByMenu(String permissionByMenu) {
        this.permissionByMenu = permissionByMenu;
    }
}
