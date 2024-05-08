package kpring.user.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // BAD_REQUEST
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 4000, "Invalid email"),
    ALREADY_EXISTS_EMAIL(HttpStatus.BAD_REQUEST, 4001, "Email already exists"),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, 4002, "Password does not match"),
    NOT_ALLOWED(HttpStatus.FORBIDDEN, 4003, "Not allowed"), // 권한이 없는 경우
    ;
    public final HttpStatus status;
    public final int code;
    public final String message;

    ErrorCode(HttpStatus status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public boolean isServerError() {
        return status.is5xxServerError();
    }
}
