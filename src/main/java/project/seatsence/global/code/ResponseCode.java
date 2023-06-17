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

    /** 5xx : Server 오류 */
    INTERNAL_ERROR(INTERNAL_SERVER_ERROR, "500_INTERNAL_SERVER_ERROR", "서버와의 연결에 실패하였습니다."),


    /** User */
    USER_NOT_FOUND(NOT_FOUND, "USER_404_001", "찾을 수 없는 이메일 혹은 비밀번호입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
