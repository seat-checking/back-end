package project.seatsence.global.token;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {
    private String email;
    private String credentials;

    public CustomAuthenticationToken(
            String email, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.email = email;
        this.credentials = credentials;
    }

    public CustomAuthenticationToken(String email, String credentials) {
        super(null);
        this.email = email;
        this.credentials = credentials;
    }

    public CustomAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.email;
    }
}
