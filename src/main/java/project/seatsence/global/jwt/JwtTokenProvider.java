package project.seatsence.global.jwt;

import io.jsonwebtoken.*;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${JWT_SECRET_KEY}")
    private String secretKey;

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
