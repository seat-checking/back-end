package project.seatsence.global.config.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import project.seatsence.global.code.ResponseCode;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.service.UserSignInService;

import static project.seatsence.global.code.ResponseCode.USER_NOT_FOUND;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private UserSignInService userSignInService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        String email = token.getName();
        String password = (String) token.getCredentials();

        User user = userSignInService.findUserByUserEmail(email);

        if(!user.getPassword().equals(password)) {
            throw new BaseException(USER_NOT_FOUND);
        }
        return new UsernamePasswordAuthenticationToken(user, password);
    }
}
