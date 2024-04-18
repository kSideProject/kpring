package kpring.auth.api.error

import io.jsonwebtoken.ExpiredJwtException
import kpring.auth.exception.TokenExpiredException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.MissingRequestValueException
import org.springframework.web.server.ServerWebInputException

@RestControllerAdvice
class ErrorApi {

    private val logger = LoggerFactory.getLogger(ErrorApi::class.java)

    @ExceptionHandler(
        WebExchangeBindException::class,
        IllegalArgumentException::class,
        MissingRequestValueException::class,
        ServerWebInputException::class
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleWhenRequestValidationFailed() {
    }

    @ExceptionHandler(ExpiredJwtException::class, TokenExpiredException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleWhenTokenIsExpired() {
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleInternalServerError(ex: Exception) {
        logger.error("", ex)
    }

}