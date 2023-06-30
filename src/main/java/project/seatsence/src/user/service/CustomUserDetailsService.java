package project.seatsence.src.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import project.seatsence.global.code.ResponseCode;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.user.dao.CustomUserDetailsRepository;
import project.seatsence.src.user.dto.CustomUserDetailsDto;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomUserDetailsRepository customUserDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUserDetailsDto user = customUserDetailsRepository.findByEmail(username);

        if (user == null) {
            throw new BaseException(ResponseCode.USER_NOT_FOUND);
        }
        return user;
    }
}
