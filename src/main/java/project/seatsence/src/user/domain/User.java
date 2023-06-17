package project.seatsence.src.user.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @NotBlank(message = "이메일이 입력되지 않았습니다.")
    private String email;

    @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Nullable private String employerIdNumber;

    @NotNull(message = "나이가 입력되지 않았습니다.")
    private int age;

    @NotBlank(message = "닉네임이 입력되지 않았습니다.")
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "성별이 체크되지 않았습니다.")
    private UserSex sex;

    @NotNull(message = "마케팅 정보 수신 동의 여부가 체크되지 않았습니다.")
    private Boolean consentToMarketing;

    @NotNull(message = "이용 약관 동의 여부가 체크되지 않았습니다.")
    private Boolean consentToTermsOfUser;

    @Builder
    public User(
            String email,
            String password,
            UserRole role,
            String employerIdNumber,
            int age,
            String nickname,
            UserSex sex,
            Boolean consentToMarketing,
            Boolean consentToTermsOfUser,
            PasswordEncoder encoder) {
        this.email = email;
        this.password = encoder.encode(password);
        this.role = role;
        this.employerIdNumber = employerIdNumber;
        this.age = age;
        this.nickname = nickname;
        this.sex = sex;
        this.consentToMarketing = consentToMarketing;
        this.consentToTermsOfUser = consentToTermsOfUser;
    }
}
