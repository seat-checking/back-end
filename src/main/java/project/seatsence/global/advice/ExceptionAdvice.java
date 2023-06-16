package project.seatsence.global.advice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static project.seatsence.global.code.ResponseCode.INTERNAL_ERROR;
import static project.seatsence.global.code.ResponseCode.INVALID_FIELD_VALUE;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.seatsence.global.code.ResponseCode;
import project.seatsence.global.exceptions.BaseDynamicException;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.global.response.ErrorResponse;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    /** Request body 파라미터에 대한 예외 처리 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        final ErrorResponse response = new ErrorResponse(INVALID_FIELD_VALUE, e.getBindingResult());
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        final ErrorResponse response = new ErrorResponse(INVALID_FIELD_VALUE, e.getBindingResult());
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    /** 서버 내부에서 동적으로 발생한 로직에 대해 처리 */
    @ExceptionHandler(BaseDynamicException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseDynamicException exception) {
        log.warn("BaseDynamicException. error message: {}", exception.getMessage());
        final ErrorResponse response =
                ErrorResponse.builder()
                        .isSuccess(false)
                        .code(exception.getCode())
                        .status(exception.getStatus())
                        .message(exception.getMessage())
                        .build();
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getStatus()));
    }

    /** 정의된 에러 처리 */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException exception) {
        log.warn("BaseException. error message: {}", exception.getMessage());
        final ResponseCode responseCode = exception.getResponseCode();
        final ErrorResponse response = new ErrorResponse(responseCode);
        return new ResponseEntity<>(response, responseCode.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> HandleException(Exception exception) {
        log.error("Exception has occurred. ", exception);
        final ErrorResponse response = new ErrorResponse(INTERNAL_ERROR);
        return new ResponseEntity<>(response, INTERNAL_SERVER_ERROR);
    }
}
