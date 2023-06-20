package project.seatsence.global.code;

import static org.springframework.http.HttpStatus.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/** 에러 및 성공 코드 관리 */
@Getter
@AllArgsConstructor
public enum ResponseCode {

    /** 2xx : 성공 */
    SUCCESS(OK, "200_OK", "요청에 성공하였습니다."),

    /** 4xx : Client 오류 */
    INVALID_FIELD_VALUE(BAD_REQUEST, "400_BAD_REQUEST", "잘못된 요청입니다."),
    ENUM_VALUE_NOT_FOUND(NOT_FOUND, "404_NOT_FOUND", "존재하지 않는 ENUM 값입니다."),

    /** 5xx : Server 오류 */
    INTERNAL_ERROR(INTERNAL_SERVER_ERROR, "500_INTERNAL_SERVER_ERROR", "서버와의 연결에 실패하였습니다."),

    /** Store error code */
    STORE_NOT_FOUND(NOT_FOUND, "STORE_404_001", "가게를 찾을 수 없습니다."),
    STORE_CATEGORY_NOT_FOUND(NOT_FOUND, "STORE_404_002", "카테고리를 찾을 수 없습니다."),

    /** Auth error code */
    TOKEN_IS_NULL(BAD_REQUEST, "AUTH_400_001", "Token 정보가 입력되지않았습니다."),
    ACCESS_TOKEN_EXPIRED(UNAUTHORIZED, "AUTH_401_001", "Access Token이 만료되었습니다."),
    ACCESS_TOKEN_TAMPERED(UNAUTHORIZED, "AUTH_401_002", "Access Token이 변조되었습니다."),

    /** User error code */
    USER_NOT_FOUND(NOT_FOUND, "USER_404_001", "찾을 수 없는 이메일 혹은 비밀번호입니다."),
    USER_EMAIL_ALREADY_EXIST(BAD_REQUEST, "USER_400_001", "해당 이메일로 가입된 유저가 이미 존재합니다."),
    USER_NICKNAME_ALREADY_EXIST(BAD_REQUEST, "USER_400_002", "해당 닉네임으로 가입된 유저가 이미 존재합니다."),
    USER_MISMATCHED_PASSWORD(BAD_REQUEST, "USER_400_003", "비밀번호가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
