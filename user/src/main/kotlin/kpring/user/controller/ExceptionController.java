package kpring.user.controller;

import kpring.user.dto.result.FailMessageResponse;
import kpring.user.exception.ExceptionWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Void> handleRuntimeException(RuntimeException e) {
        log.error("Internal server error", e);
        return ResponseEntity.internalServerError().build();
    }
}
