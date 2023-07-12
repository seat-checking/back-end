package project.seatsence.src.user.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.seatsence.global.code.ResponseCode;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.dto.CustomUserDetailsDto;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserSignInService userService;

    public CustomUserDetailsService(UserSignInService service) {
        this.userService = service;
    }

    @Override
    public CustomUserDetailsDto loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userService.findUserByUserEmail(username);
        if (user == null) {
            throw new BaseException(ResponseCode.USER_NOT_FOUND);
        }

        List<String> roles = new ArrayList<>();
        roles.add(user.getRole().toString());

        CustomUserDetailsDto userDetailsDto =
                new CustomUserDetailsDto(
                        user.getEmail(),
                        user.getPassword(),
                        user.getState(),
                        user.getNickname(),
                        roles);
        return userDetailsDto;
    }
}
