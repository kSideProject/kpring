package kpring.user.exception;

public class ExceptionWrapper extends RuntimeException {

    public final ErrorCode errorCode;
    public ExceptionWrapper(ErrorCode code) {
        this.errorCode = code;
    }

    public ExceptionWrapper(ErrorCode code, Throwable cause) {
        super(cause);
        this.errorCode = code;
    }
}
