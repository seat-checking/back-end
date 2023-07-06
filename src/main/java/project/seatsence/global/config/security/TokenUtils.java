package project.seatsence.global.config.security;

import static project.seatsence.global.code.ResponseCode.INVALID_TOKEN;
import static project.seatsence.global.constants.Constants.TOKEN_ISSUER;
import static project.seatsence.src.auth.domain.TokenType.ACCESS_TOKEN;
import static project.seatsence.src.auth.domain.TokenType.REFRESH_TOKEN;

import io.jsonwebtoken.*;
import java.security.Key;
import java.util.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.user.domain.User;

/**
 * JWT Util
 *
 * @author benjaminuj
 * @fileName TokenUtils
 * @since 2023.06.19
 */
@Log4j2
@Component
public class TokenUtils {
    private static String secretKey;

    private Key key;

    @Value("${JWT_SECRET_KEY}")
    public void setSecretKey(String key) {
        secretKey = key;
    }

    /**
     * 사용자 정보 기반 Token 생성
     *
     * @param user : 사용자 정보
     * @return String : Token
     */
    public static String generateAccessToken(User user) {
        return Jwts.builder()
                .setHeader(createHeader()) // Header
                .setIssuer(TOKEN_ISSUER) // Payload - Claims
                .setSubject(user.getEmail()) // Payload - Claims
                .setClaims(createAccessTokenClaims(user)) // Payload - Claims
                .setExpiration(createAccessTokenExpiredDate()) // Payload - Claims
                .signWith(createSignature()) // Signature
                .compact();
    }

    public static String generateRefreshToken(User user) {
        return Jwts.builder()
                .setHeader(createHeader()) // Header
                .setIssuer(TOKEN_ISSUER) // Payload - Claims
                .setSubject(user.getEmail()) // Payload - Claims
                .setClaims(createRefreshTokenClaims(user)) // Payload - Claims
                .setExpiration(createAccessTokenExpiredDate()) // Payload - Claims
                .signWith(createSignature()) // Signature
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
     * Token 만료기간 지정
     *
     * @return Date : access token 만료기간
     */
    private static Date createAccessTokenExpiredDate() {
        Calendar issuedAt = Calendar.getInstance();
        issuedAt.add(Calendar.MINUTE, 30); // 30분

        return issuedAt.getTime();
    }

    /**
     * 사용자 정보 기반 Claim 생성
     *
     * @param user 사용자 정보
     * @return Map<String, Object>
     */
    private static Map<String, Object> createAccessTokenClaims(User user) {
        // 공개 Claim에 사용자 이메일과 닉네임 설정해 정보를 조회할 수 있습니다
        Map<String, Object> claims = new HashMap<>();

        claims.put("userEmail", user.getEmail());
        claims.put("userNickname", user.getNickname());
        claims.put("type", ACCESS_TOKEN);
        return claims;
    }

    private static Map<String, Object> createRefreshTokenClaims(User user) {
        // 공개 Claim에 사용자 이메일과 닉네임 설정해 정보를 조회할 수 있습니다
        Map<String, Object> claims = new HashMap<>();

        claims.put("userEmail", user.getEmail());
        claims.put("userNickname", user.getNickname());
        claims.put("type", REFRESH_TOKEN);
        return claims;
    }

    /**
     * 토큰 유효성 확인
     *
     * @param token
     * @return boolean : 유효한지 유효하지 않은지 여부 반환
     */
    public static boolean isValidToken(String token) {
        boolean isValid = true;

        try {
            Claims claims = getClaimsFromToken(token);

            log.info("expireTime : " + claims.getExpiration());
            log.info("userEmail : " + claims.get("userEmail"));
            log.info("userNickname : " + claims.get("userNickname"));

            isValid = true;
        } catch (ExpiredJwtException expiredJwtException) {
            log.error("Token Expired");
            isValid = false;
        } catch (JwtException jwtException) {
            log.error("Token Tampered");
            isValid = false;
        } catch (NullPointerException nullPointerException) {
            log.error("Token is null");
            isValid = false;
        }
        return isValid;
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

    /**
     * 통신시 Header에 담긴 Token을 추출
     *
     * @param header
     * @return String : Token
     */
    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }

    /**
     * Token에서 Claims 반환
     *
     * @param token
     * @return Claims
     */
    private static Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Token에서 사용자 닉네임 정보 반환
     *
     * @param token
     * @return String : 사용자 닉네임
     */
    public static String getUserNicknameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("userNickname").toString();
    }

    /**
     * Token에서 사용자 이메일 정보 반환
     *
     * @param token
     * @return String : 사용자 이메일
     */
    public static String getUserEmailFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("userEmail").toString();
    }

    /** 자동 로그인 위해 만료된 엑세스 토큰 검증 */
    public void validateExpiredAccessToken(String token) {
        try {
            if (token != null && !token.isEmpty()) {
                parse(token);
            }
        } catch (ExpiredJwtException e) {
            return;
        }
        throw new BaseException(INVALID_TOKEN);
    }

    private Jws<Claims> parse(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
    }
}
