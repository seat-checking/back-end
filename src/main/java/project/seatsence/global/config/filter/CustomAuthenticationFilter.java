package project.seatsence.global.config.filter;

import static project.seatsence.global.code.ResponseCode.USER_NOT_FOUND;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.user.dto.request.UserSignInRequest;

/** 이메일과 비밀번호 기반의 데이터를 전송받아 '인증'을 담당하는 필터입니다. */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request)
            throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserSignInRequest userSignIn =
                    objectMapper.readValue(request.getInputStream(), UserSignInRequest.class);

            return new UsernamePasswordAuthenticationToken(
                    userSignIn.getEmail(), userSignIn.getPassword());
        } catch (UsernameNotFoundException usernameNotFoundException) {
            throw new BaseException(USER_NOT_FOUND);
        }
    }
}
