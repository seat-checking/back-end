package project.seatsence.global.config.handler;

import org.json.simple.JSONObject;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletRequest reponse,
                                        AuthenticationException exception) throws IOException {

        JSONObject
    }
}
