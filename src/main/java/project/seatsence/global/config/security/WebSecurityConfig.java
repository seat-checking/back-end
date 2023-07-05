package project.seatsence.global.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import project.seatsence.global.config.filter.CustomAuthenticationFilter;
import project.seatsence.global.config.handler.CustomAuthFailureHandler;
import project.seatsence.global.config.handler.CustomAuthSuccessHandler;
import project.seatsence.global.config.handler.CustomAuthenticationProvider;
import project.seatsence.src.user.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired private CustomUserDetailsService userDetailsService;

    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web ->
                web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests(authz -> authz.anyRequest().permitAll())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                .disable()
                .addFilterAfter(customAuthenticationFilter(), CsrfFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider(userDetailsService, bCryptPasswordEncoder);
    }

    //    @Bean
    //    public BCryptPasswordEncoder bCryptPasswordEncoder() {
    //        return new BCryptPasswordEncoder();
    //    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() {
        CustomAuthenticationFilter filter =
                new CustomAuthenticationFilter(
                        new AntPathRequestMatcher("/api/v1/sign-in", HttpMethod.POST.name()));

        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(customSignInSuccessHandler());
        filter.setAuthenticationFailureHandler(customSignInFailureHandler());
        return filter;
    }

    @Bean
    public CustomAuthSuccessHandler customSignInSuccessHandler() {
        return new CustomAuthSuccessHandler();
    }

    @Bean
    public CustomAuthFailureHandler customSignInFailureHandler() {
        return new CustomAuthFailureHandler();
    }
}
