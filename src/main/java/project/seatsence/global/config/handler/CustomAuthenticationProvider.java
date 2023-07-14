// package project.seatsence.global.config.handler;
//
// import static project.seatsence.global.code.ResponseCode.USER_NOT_FOUND;
//
// import lombok.AllArgsConstructor;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Component;
// import project.seatsence.global.exceptions.BaseException;
// import project.seatsence.src.user.dto.CustomUserDetailsDto;
// import project.seatsence.src.user.service.CustomUserDetailsService;
//
// @Component
// @AllArgsConstructor
// public class CustomAuthenticationProvider implements AuthenticationProvider {
//    private CustomUserDetailsService userDetailsService;
//    private BCryptPasswordEncoder passwordEncoder;
//
//
//    @Override
//    public Authentication authenticate(Authentication authentication)
//            throws AuthenticationException {
//                UsernamePasswordAuthenticationToken token =
//                        (UsernamePasswordAuthenticationToken) authentication;
//
//        String email = authentication.getName();
//        String password = (String) authentication.getCredentials();
//
//        CustomUserDetailsDto user =
//                (CustomUserDetailsDto) userDetailsService.loadUserByUsername(email);
//
////                if (!customUserDetailsDto.getPassword().equals(password)) {
////                    throw new BaseException(USER_NOT_FOUND);
////                }
//
//        if (!this.passwordEncoder.matches(password, user.getPassword())) {
//            throw new BaseException(USER_NOT_FOUND);
//        }
//
//        return new UsernamePasswordAuthenticationToken(email, password, user.getAuthorities());
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
// }
