package project.seatsence.global.constants;

public class Constants {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_NAME = "refreshToken";
    public static final String COOKIE_NAME_PREFIX_SECURE = "__Secure-";
    public static final String TOKEN_AUTH_TYPE = "Bearer ";
    public static final String TOKEN_ISSUER = "SEAT_SENSE";
    public static final String TOKEN_TYPE = "type";
    public static final String[] SwaggerPatterns = {
        "/swagger-resources/**", "/swagger-ui/**", "/v2/api-docs/**", "/v2/api-docs", "/api-docs/**"
    };

    public static final int UTILIZATION_TIME_UNIT = 30; // 30분 단위

    public static final int MIN_HOURS_FOR_SAME_DAY_RESERVATION =
            3; // 당일 예약 가능한 시작 시간 = 현시간 기준 '3'시간 이후
}
