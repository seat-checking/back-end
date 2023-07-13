package project.seatsence.src.user.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.seatsence.global.entity.BaseTimeAndStateEntity;

@Getter
@AllArgsConstructor
public class CustomUserDetailsDto implements UserDetails {
    // todo : entity or custom choice
    private String email;
    private String password;
    private BaseTimeAndStateEntity.State state;
    private String nickname;

    private List<String> roles = new ArrayList<>();

    //    @Delegate private User user; // todo : entity or custom choice

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
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
