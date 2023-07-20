package project.seatsence.global.config.filter;

import static project.seatsence.global.constants.Constants.AUTHORIZATION_HEADER;
import static project.seatsence.global.constants.Constants.TOKEN_AUTH_TYPE;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import project.seatsence.global.config.security.JwtProvider;
import project.seatsence.src.auth.domain.JwtState;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Autowired
    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (isIgnoredUrl(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = resolveTokenFromHeader(request, AUTHORIZATION_HEADER);

        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (accessToken != null && jwtProvider.validateToken(accessToken) == JwtState.ACCESS) {
            // 권한부여
            Authentication authentication = jwtProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if (accessToken != null
                && jwtProvider.validateToken(accessToken) == JwtState.EXPIRED) {
            String refreshToken = resolveTokenFromCookie(request);
            if (refreshToken != null
                    && jwtProvider.validateToken(refreshToken) == JwtState.ACCESS) {
                String newRefreshToken = jwtProvider.reIssueRefreshToken(refreshToken);
                if (newRefreshToken != null) {
                    Cookie refreshTokenCookie = jwtProvider.createCookie(newRefreshToken);
                    response.addCookie(refreshTokenCookie);

                    Authentication authentication = jwtProvider.getAuthentication(refreshToken);
                    response.setHeader(
                            AUTHORIZATION_HEADER,
                            TOKEN_AUTH_TYPE
                                    + jwtProvider.generateAccessToken(
                                            jwtProvider.findCustomUserDetailsByUsername(
                                                    authentication)));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("Reissue refresh token and access token!");
                }
            }
        } else {
            log.info("No valid JWT found, uri {} : ", request.getRequestURI());
        }

        filterChain.doFilter(request, response);
    }

    private String resolveTokenFromHeader(HttpServletRequest request, String headerName) {
        String rawHeader = request.getHeader(headerName);
        return jwtProvider.getTokenFromHeader(rawHeader);
    }

    private String resolveTokenFromCookie(HttpServletRequest request) {
        Cookie[] rc = request.getCookies();
        String refreshtoken = null;
        for (Cookie cookie : rc) {
            if (cookie.getName().equals("refreshtoken")) {
                refreshtoken = cookie.getValue();
            }
        }
        return refreshtoken;
    }

    private boolean isIgnoredUrl(HttpServletRequest request) {
        String path = request.getRequestURI().substring(request.getContextPath().length());

        return path.startsWith("/swagger")
                || path.startsWith("/swagger-ui/")
                || path.startsWith("/swagger-resources/")
                || path.startsWith("/v2/api-docs")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/webjars")
                || path.startsWith("/swagger-ui.html")
                || path.startsWith("/api-docs/swagger-config");
    }
}
