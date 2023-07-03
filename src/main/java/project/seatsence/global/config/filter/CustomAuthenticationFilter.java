package project.seatsence.global.config.filter;

import static project.seatsence.global.code.ResponseCode.GENERATE_ACCESS_TOKEN_FAIL;
import static project.seatsence.global.code.ResponseCode.USER_NOT_FOUND;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.global.token.CustomAuthenticationToken;
import project.seatsence.src.user.dto.request.UserSignInRequest;

import java.io.IOException;

public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public CustomAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

//    @Override
//    public Authentication attemptAuthentication(
//            HttpServletRequest request, HttpServletResponse response)
//            throws AuthenticationException {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            UserSignInRequest userSignIn =
//                    objectMapper.readValue(request.getInputStream(), UserSignInRequest.class);
//
//            UsernamePasswordAuthenticationToken token =
//                    new UsernamePasswordAuthenticationToken(
//                            userSignIn.getEmail(), userSignIn.getPassword());
//            return this.getAuthenticationManager().authenticate(token);
//        } catch (UsernameNotFoundException usernameNotFoundException) {
//            throw new BaseException(USER_NOT_FOUND);
//        } catch (Exception e) {
//            throw new BaseException(GENERATE_ACCESS_TOKEN_FAIL);
//        }
//    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String email = request.getParameter("email");
        String credentials = request.getParameter("password");

        return getAuthenticationManager().authenticate(new CustomAuthenticationToken(email, credentials));
    }
}
