package project.seatsence.global.jwt;

import io.jsonwebtoken.*;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider implements InitializingBean {

    private final String secretKey;
    private final Long accessTokenExp;

    private Key key;

    public JwtTokenProvider(
            @Value("${JWT_SECRET_KEY}") String secretKey,
            @Value("${JWT_ACCESS_EXP}") Long accessTokenExp) {
        this.secretKey = secretKey;
        this.accessTokenExp=accessTokenExp*1000;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(Long id) {
        Date issuedAt = new Date();
        Date accessTokenExpires = new Date(issuedAt.getTime() + 1800000); // 30분

        return buildAccessToken(id, issuedAt, accessTokenExpires);
    }

    private String buildAccessToken(Long id, Date issuedAt, Date accessTokenExpires) {
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .setIssuer("SEAT_SENSE")
                .setIssuedAt(issuedAt)
                .setSubject(id.toString())
                .claim("type", "ACCESS_TOKEN")
                .setExpiration(accessTokenExpires)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
