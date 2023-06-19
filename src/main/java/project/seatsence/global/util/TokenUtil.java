package project.seatsence.global.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import project.seatsence.src.user.domain.User;

public class TokenUtil {
    @Value("${JWT_SECRET_KEY}")
    private static String secretKey;

    @Value("${JWT_ACCESS_EXP}")
    private static Long accessTokenExp;

    private Key key;

    public String generateAccessToken(User user) {
        Date issuedAt = new Date();
        Date accessTokenExpires = new Date(issuedAt.getTime() + accessTokenExp * 1000); // 30분

        return buildAccessToken(user, issuedAt, accessTokenExpires);
    }

    private String buildAccessToken(User user, Date issuedAt, Date accessTokenExpires) {
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .setIssuer("SEAT_SENSE")
                .setIssuedAt(issuedAt)
                .setSubject(user.getId().toString())
                .setClaims(createClaims(user))
                .setExpiration(accessTokenExpires)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * 사용자 정보 기반 Claim 생성
     *
     * @param user 사용자 정보
     * @return Map<String, Object>
     */
    private static Map<String, Object> createClaims(User user) {
        // 공개 Claim에 사용자 이메일과 닉네임 설정해 정보를 조회할 수 있습니다
        Map<String, Object> claims = new HashMap<>();

        claims.put("userEmail", user.getEmail());
        claims.put("userNickname", user.getNickname());
        return claims;
    }

    /**
     * 토큰에서 사용자 정보 반환
     *
     * @param token
     * @return String : 사용자 정보
     */
    public static String parseTokenToUserInformation(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
