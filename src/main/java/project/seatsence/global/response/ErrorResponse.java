package project.seatsence.global.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.*;
import org.springframework.validation.BindingResult;
import project.seatsence.global.code.ResponseCode;

@AllArgsConstructor
@Builder
@Getter
public class ErrorResponse {
    private final boolean isSuccess;
    private final int status;
    private final String code;
    private final String message;
    private final List<FieldError> errors;

    public ErrorResponse(final ResponseCode code) {
        this.isSuccess = false;
        this.status = code.getStatus().value();
        this.code = code.getCode();
        this.message = code.getMessage();
        this.errors = new ArrayList<>();
    }

    public ErrorResponse(ResponseCode status, BindingResult bindingResult) {
        this.isSuccess = false;
        this.message = status.getMessage();
        this.status = status.getStatus().value();
        this.errors = FieldError.of(bindingResult);
        this.code = status.getCode();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors =
                    bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(
                            error ->
                                    new FieldError(
                                            error.getField(),
                                            error.getRejectedValue() == null
                                                    ? ""
                                                    : error.getRejectedValue().toString(),
                                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}
