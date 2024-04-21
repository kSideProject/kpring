package kpring.user.controller;

import kpring.user.dto.result.FailMessageResponse;
import kpring.user.exception.ExceptionWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<FailMessageResponse> handleExceptionWrapper(ExceptionWrapper e) {
        if (e.errorCode.isServerError()) {
            log.error("Internal server error", e);
        }

        var response = FailMessageResponse.builder()
                .message(e.errorCode.message)
                .build();

        return ResponseEntity.status(e.errorCode.status)
                .body(response
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FailMessageResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        var response = FailMessageResponse.builder()
                .message(e.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                .build();

        return ResponseEntity.badRequest()
                .body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<FailMessageResponse> handleRuntimeException(RuntimeException e) {
        log.error("Internal server error", e);
        var response = FailMessageResponse.serverError;
        return ResponseEntity.internalServerError()
                .body(response);
    }
}
