package project.seatsence.global.config.filter;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import project.seatsence.global.token.CustomAuthenticationToken;

public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public CustomAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    //        @Override
    //        public Authentication attemptAuthentication(
    //                HttpServletRequest request, HttpServletResponse response)
    //                throws AuthenticationException {
    //            try {
    //                ObjectMapper objectMapper = new ObjectMapper();
    //                UserSignInRequest userSignIn =
    //                        objectMapper.readValue(request.getInputStream(),
    // UserSignInRequest.class);
    //
    //                UsernamePasswordAuthenticationToken token =
    //                        new UsernamePasswordAuthenticationToken(
    //                                userSignIn.getEmail(), userSignIn.getPassword());
    //                return this.getAuthenticationManager().authenticate(token);
    //            } catch (UsernameNotFoundException usernameNotFoundException) {
    //                throw new BaseException(ResponseCode.USER_NOT_FOUND);
    //            } catch (Exception e) {
    //                throw new BaseException(ResponseCode.GENERATE_ACCESS_TOKEN_FAIL);
    //            }
    //        }
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String email = request.getParameter("email");
        String credentials = request.getParameter("password");

        return getAuthenticationManager()
                .authenticate(new CustomAuthenticationToken(email, credentials));
    }
}
