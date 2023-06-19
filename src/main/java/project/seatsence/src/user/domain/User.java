package project.seatsence.src.user.domain;

import javax.persistence.*;
import lombok.*;
import project.seatsence.global.entity.BaseEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
@Table(
        name = "user",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "state"})})
public class User extends BaseEntity {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserSex sex;

    @Column(nullable = false)
    private Boolean consentToMarketing;

    @Column(nullable = false)
    private Boolean consentToTermsOfUser;

    @Builder
    public User(
            String email,
            String password,
            UserRole role,
            int age,
            String nickname,
            UserSex sex,
            Boolean consentToMarketing,
            Boolean consentToTermsOfUser) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.age = age;
        this.nickname = nickname;
        this.sex = sex;
        this.consentToMarketing = consentToMarketing;
        this.consentToTermsOfUser = consentToTermsOfUser;
    }
}
