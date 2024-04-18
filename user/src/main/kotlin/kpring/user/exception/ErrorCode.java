package kpring.user.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // BAD_REQUEST
    ALREADY_EXISTS_EMAIL(HttpStatus.BAD_REQUEST, 4001, "Email already exists"),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, 4002, "Invalid email"),
    ;
    public final HttpStatus status;
    public final int code;
    public final String message;

    ErrorCode(HttpStatus status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public boolean isServerError(){
        return status.is5xxServerError();
    }
}
