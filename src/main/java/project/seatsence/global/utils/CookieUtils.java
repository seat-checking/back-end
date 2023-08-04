package project.seatsence.global.utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component // Todo : 직접 JavaConfig에 등록이 좋으려나
public class CookieUtils {

    /**
     * Refresh Token을 담는 쿠키
     *
     * @param refreshToken
     * @return void : Refresh Token을 response에 담음
     */
    public void addCookie(HttpServletResponse response, String refreshToken) {
        String cookieName = "refreshToken";
        String cookieValue = refreshToken;
        var RefreshTokenCookie = URLEncoder.encode(cookieValue, StandardCharsets.UTF_8);
        ResponseCookie cookie =
                ResponseCookie.from(cookieName, RefreshTokenCookie)
                        .httpOnly(true)
                        .sameSite("None")
                        .secure(true)
                        .path("/")
                        .maxAge(15 * 24 * 60 * 60) // 15일
                        .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }
}
