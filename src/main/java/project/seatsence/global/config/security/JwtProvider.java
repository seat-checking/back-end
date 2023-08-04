package project.seatsence.global.config.security;

import static project.seatsence.global.code.ResponseCode.*;
import static project.seatsence.global.constants.Constants.*;
import static project.seatsence.global.constants.Constants.TOKEN_AUTH_TYPE;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.*;
import static project.seatsence.src.auth.domain.TokenType.ACCESS_TOKEN;
import static project.seatsence.src.auth.domain.TokenType.REFRESH_TOKEN;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.auth.dao.RefreshTokenRepository;
import project.seatsence.src.auth.domain.JwtState;
import project.seatsence.src.auth.domain.RefreshToken;
import project.seatsence.src.user.dto.CustomUserDetailsDto;
import project.seatsence.src.user.service.UserDetailsServiceImpl;

/**
 * JWT Provider
 *
 * @author benjaminuj
 * @fileName JwtProvider
 * @since 2023.06.19
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class JwtProvider implements InitializingBean {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private static String secretKey;

    private Key key;

    @Value("${JWT_SECRET_KEY}")
    public void setSecretKey(String key) {
        secretKey = key;
    }

    public String getSecretKey() {
        return secretKey;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        key = Keys.hmacShaKeyFor(encodedKey.getBytes());
    }

    /**
     * 사용자 정보 기반 Token 생성
     *
     * @param user : 사용자 정보
     * @return String : Token
     */
    public static String generateAccessToken(CustomUserDetailsDto user) {
        return Jwts.builder()
                .setHeader(createHeader()) // Header
                .setIssuer(TOKEN_ISSUER) // Payload - Claims
                .setSubject(user.getEmail()) // Payload - Claims
                .setClaims(createAccessTokenClaims(user)) // Payload - Claims
                .setExpiration(createAccessTokenExpiredDate()) // Payload - Claims
                .signWith(createSignature()) // Signature
                .compact();
    }

    public static String generateRefreshToken(CustomUserDetailsDto user) {
        return Jwts.builder()
                .setHeader(createHeader()) // Header
                .setIssuer(TOKEN_ISSUER) // Payload - Claims
                .setSubject(user.getEmail()) // Payload - Claims
                .setClaims(createRefreshTokenClaims(user)) // Payload - Claims
                .setExpiration(createRefreshTokenExpiredDate()) // Payload - Claims
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
        issuedAt.add(Calendar.MINUTE, 1); // 30분

        return issuedAt.getTime();
    }

    /**
     * Token 만료기간 지정
     *
     * @return Date : refresh token 만료기간
     */
    private static Date createRefreshTokenExpiredDate() {
        Calendar issuedAt = Calendar.getInstance();
        issuedAt.add(Calendar.MINUTE, 2); // 30분
        /*issuedAt.add(Calendar.DAY_OF_MONTH, 2); // 2주*/

        return issuedAt.getTime();
    }

    /**
     * 사용자 정보 기반 Claim 생성 - access token용
     *
     * @param user 사용자 정보가 담긴 객체
     * @return Map<String, Object>
     */
    private static Map<String, Object> createAccessTokenClaims(CustomUserDetailsDto user) {
        // 공개 Claim에 사용자 이메일과 닉네임 설정해 정보를 조회할 수 있습니다
        Map<String, Object> claims = new HashMap<>();

        claims.put("email", user.getEmail());
        claims.put("nickname", user.getNickname());
        claims.put(TOKEN_TYPE, ACCESS_TOKEN);
        return claims;
    }

    /**
     * 사용자 정보 기반 Claim 생성 - refresh token용
     *
     * @param user 사용자 정보가 담긴 객체
     * @return Map<String, Object>
     */
    private static Map<String, Object> createRefreshTokenClaims(CustomUserDetailsDto user) {
        // 공개 Claim에 사용자 이메일과 닉네임 설정해 정보를 조회할 수 있습니다
        Map<String, Object> claims = new HashMap<>();

        claims.put("email", user.getEmail());
        claims.put("nickname", user.getNickname());
        claims.put(TOKEN_TYPE, REFRESH_TOKEN);
        return claims;
    }

    /**
     * JWT Signature 발급
     *
     * @return Key
     */
    private static Key createSignature() {
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secretKey);
        return new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS256.getJcaName());
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
            log.info("email : " + claims.get("email"));
            log.info("nickname : " + claims.get("nickname"));

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
     * 통신시 Header에 담긴 Token을 추출
     *
     * @param header
     * @return String : Token
     */
    public static String getTokenFromHeader(String header) {
        if (header != null
                && header.length() > TOKEN_AUTH_TYPE.length()
                && header.startsWith(TOKEN_AUTH_TYPE)) {
            return header.substring(TOKEN_AUTH_TYPE.length());
        }
        return null;
    }

    /**
     * Token에서 Claims 반환
     *
     * @param token
     * @return Claims
     */
    public static Claims getClaimsFromToken(String token) {
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
        return claims.get("nickname").toString();
    }

    /**
     * Token에서 사용자 이메일 정보 반환
     *
     * @param token
     * @return String : 사용자 이메일
     */
    public static String getUserEmailFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("email").toString();
    }

    private Jws<Claims> getJws(String token) {
        try {
            return parse(token);
        } catch (ExpiredJwtException e) {
            throw new BaseException(ACCESS_TOKEN_EXPIRED);
        } catch (Exception e) {
            throw new BaseException(INVALID_TOKEN);
        }
    }

    private Jws<Claims> parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .build()
                .parseClaimsJws(token);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getJws(token).getBody();
        UserDetails userDetails =
                userDetailsServiceImpl.loadUserByUsername(claims.get("email", String.class));

        return new UsernamePasswordAuthenticationToken(
                userDetails, token, userDetails.getAuthorities());
    }

    public String parseRefreshToken(String token) {
        try {
            if (isRefreshToken(token)) {
                Claims claims = parse(token).getBody();
                return claims.getSubject();
            }
        } catch (ExpiredJwtException e) {
            throw new BaseException(REFRESH_TOKEN_EXPIRED);
        } catch (BaseException e) { // Todo : BaseException 발생 체크
            if (e.getResponseCode().equals(ACCESS_TOKEN_EXPIRED))
                throw new BaseException(REFRESH_TOKEN_EXPIRED);
        }
        throw new BaseException(INVALID_TOKEN);
    }

    public JwtState validateToken(String token) {
        try {
            parse(token);
            return JwtState.ACCESS;
        } catch (ExpiredJwtException e) {
            return JwtState.EXPIRED;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("jwtException : {}", e);
        }
        return JwtState.DENIED;
    }

    public boolean isAccessToken(String token) {
        return getJws(token).getBody().get(TOKEN_TYPE).equals(ACCESS_TOKEN);
    }

    public boolean isRefreshToken(String token) {
        return getJws(token).getBody().get(TOKEN_TYPE).equals(REFRESH_TOKEN);
    }

    // Todo : 예외 응답으로도 띄우기
    @Transactional
    public String reIssueRefreshToken(String refreshToken) throws RuntimeException {
        Authentication authentication = getAuthentication(refreshToken);
        RefreshToken findedRefreshToken =
                refreshTokenRepository
                        .findByEmailAndState(authentication.getName(), ACTIVE)
                        .orElseThrow(
                                () ->
                                        new UsernameNotFoundException(
                                                "authentication.getName() : "
                                                        + authentication.getName()
                                                        + " was not found"));

        CustomUserDetailsDto user = findCustomUserDetailsByUsername(authentication);
        if (findedRefreshToken.getRefreshToken().equals(refreshToken)) {
            String newRefreshToken = generateRefreshToken(user);
            findedRefreshToken.setRefreshToken(newRefreshToken); // Todo : transaction flush check
            return newRefreshToken;
        } else {
            log.info("refresh token이 일치하지 않습니다.");
            return null;
        }
    }

    public CustomUserDetailsDto findCustomUserDetailsByUsername(Authentication authentication) {
        return userDetailsServiceImpl.loadUserByUsername(authentication.getName());
    }

    @Transactional
    public String issueRefreshToken(CustomUserDetailsDto user) {
        String refreshToken = generateRefreshToken(user);
        refreshTokenRepository
                .findByEmailAndState(user.getEmail(), ACTIVE) // Todo : State가 ACTIVE인 것도 추가
                .ifPresentOrElse(
                        r -> {
                            r.setRefreshToken(
                                    refreshToken); // Todo : DB의 값 refreshToken값으로 변경 필요하지않나?
                        },
                        () -> {
                            RefreshToken newRefreshToken =
                                    new RefreshToken(user.getEmail(), refreshToken);
                            refreshTokenRepository.save(newRefreshToken);
                        });
        return refreshToken;
    }
}
