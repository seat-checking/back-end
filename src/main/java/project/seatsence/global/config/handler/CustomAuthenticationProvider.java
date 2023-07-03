package project.seatsence.global.config.handler;

import static project.seatsence.global.code.ResponseCode.USER_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.dto.CustomUserDetailsDto;
import project.seatsence.src.user.service.CustomUserDetailsService;
import project.seatsence.src.user.service.UserSignInService;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        UsernamePasswordAuthenticationToken token =
                (UsernamePasswordAuthenticationToken) authentication;

        String email = token.getName();
        String password = (String) token.getCredentials();

        CustomUserDetailsDto customUserDetailsDto = (CustomUserDetailsDto) customUserDetailsService.loadUserByUsername(email);

        if (!customUserDetailsDto.getPassword().equals(password)) {
            throw new BaseException(USER_NOT_FOUND);
        }
        return new UsernamePasswordAuthenticationToken(customUserDetailsDto, password, customUserDetailsDto.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
