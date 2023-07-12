package project.seatsence.global.config.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import project.seatsence.global.code.ResponseCode;
import project.seatsence.global.config.security.JwtProvider;
import project.seatsence.global.constants.Constants;
import project.seatsence.global.entity.BaseTimeAndStateEntity;
import project.seatsence.src.user.dto.CustomUserDetailsDto;

@Configuration
public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        CustomUserDetailsDto user = ((CustomUserDetailsDto) authentication.getPrincipal());

        HashMap<String, Object> responseMap = new HashMap<>();

        JSONObject jsonObject;

        if (user.getState().equals(BaseTimeAndStateEntity.State.INACTIVE)) {
            responseMap.put("isSuccess", true);
            responseMap.put("status", 200);
            responseMap.put("code", ResponseCode.INACTIVE_USER.getCode());
            responseMap.put("message", ResponseCode.INACTIVE_USER.getMessage());
            responseMap.put("result", null);
            jsonObject = new JSONObject(responseMap);
        } else {
            String accessToken = JwtProvider.generateAccessToken(user);

            responseMap.put("isSuccess", true);
            responseMap.put("status", 200);
            responseMap.put("code", null);
            responseMap.put("message", null);
            responseMap.put("result", Constants.TOKEN_AUTH_TYPE + accessToken);
            jsonObject = new JSONObject(responseMap);
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jsonObject);
        printWriter.flush();
        printWriter.close();
    }
}
