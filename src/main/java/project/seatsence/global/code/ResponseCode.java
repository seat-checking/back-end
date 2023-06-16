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

    /** 400 : Request, Response 오류 */
    INVALID_FIELD_VALUE(BAD_REQUEST, "400_BAD_REQUEST", "필드 값이 올바르지 않습니다."),

    /** 5xx : Server 오류 */
    INTERNAL_ERROR(INTERNAL_SERVER_ERROR, "500_INTERNAL_SERVER_ERROR", "서버와의 연결에 실패하였습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
