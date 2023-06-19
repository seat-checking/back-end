package project.seatsence.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider implements InitializingBean {
    private static final String AUTHORITIES_KEY = "auth";

    private final String secretKey;
    private final Long accessTokenExp;

    private Key key;

    public JwtProvider(
            @Value("${JWT_SECRET_KEY}") String secretKey,
            @Value("${JWT_ACCESS_EXP}") Long accessTokenExp) {
        this.secretKey = secretKey;
        this.accessTokenExp = accessTokenExp * 1000;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
}
