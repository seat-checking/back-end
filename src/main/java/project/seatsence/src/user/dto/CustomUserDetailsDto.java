package project.seatsence.src.user.dto;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.seatsence.global.entity.BaseTimeAndStateEntity;

@Getter
@AllArgsConstructor
public class CustomUserDetailsDto implements UserDetails {
    private String email;
    private String password;
    private BaseTimeAndStateEntity.State state;
    private String nickname;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (state.equals(BaseTimeAndStateEntity.State.ACTIVE)) {
            return true;
        } else {
            return false;
        }
    }
}
