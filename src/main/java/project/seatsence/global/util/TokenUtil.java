package project.seatsence.global.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
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
                .setHeader(createHeader()) // Header
                .setIssuer("SEAT_SENSE") // Payload - Claim
                .setIssuedAt(issuedAt) // Payload - Claim
                .setSubject(user.getId().toString()) // Payload - Claim
                .setClaims(createClaims(user)) // Payload - Claim
                .setExpiration(accessTokenExpires)
                .signWith(SignatureAlgorithm.HS256, createSignature())
                .compact();
    }

    /**
     * JWT의 Header 값을 생성헤주는 메서드
     *
     * @return Map<String, Object>
     */
    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        return header;
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

    /**
     * JWT Signature 발급
     *
     * @return Key
     */
    private static Key createSignature() {
        byte[] keySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        return new SecretKeySpec(keySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }
}
