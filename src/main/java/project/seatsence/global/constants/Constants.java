package project.seatsence.global.constants;

public class Constants {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "Refresh";
    public static final String TOKEN_AUTH_TYPE = "Bearer ";
    public static final String TOKEN_ISSUER = "SEAT_SENSE";
    public static final String TOKEN_TYPE = "type";
    public static final String[] SwaggerPatterns = {
        "/swagger-resources/**", "/swagger-ui/**", "/v2/api-docs/**", "/v2/api-docs", "/api-docs/**"
    };

    public static final int RESERVATION_TIME_UNIT = 30; // 30분 단위
}
