package kpring.auth.api.error

import io.jsonwebtoken.ExpiredJwtException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException

@RestControllerAdvice
class ErrorApi {

    private val logger = LoggerFactory.getLogger(ErrorApi::class.java)

    @ExceptionHandler(ExpiredJwtException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleWhenTokenIsExpired(){
    }
    @ExceptionHandler(WebExchangeBindException::class, IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleWhenRequestValidationFailed() {
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleInternalServerError(ex : Exception) {
        logger.error("", ex)
    }

}