package project.seatsence.src.auth.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.seatsence.global.entity.BaseEntity;

@Entity
@Getter
@RequiredArgsConstructor
public class RefreshToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String refreshToken;

    public RefreshToken(String email, String refreshToken) {
        this.email = email;
        this.refreshToken = refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
