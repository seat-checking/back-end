package project.seatsence.global.config.jwt;

import lombok.RequiredArgsConstructor;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${JWT_SECRETKEY}")
    private String secretKey;

    public String generateAccessToken(Long id) {
        Date issuedAt = new Date();
        Date accessTokenExpires =
                new Date(issuedAt.getTime() + 1800000); //30분

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
