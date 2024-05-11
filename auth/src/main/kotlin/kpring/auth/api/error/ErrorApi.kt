package kpring.auth.api.error

import io.jsonwebtoken.ExpiredJwtException
import kpring.auth.error.TokenExpiredException
import kpring.core.global.dto.response.ErrorResponse
import kpring.core.global.exception.ServiceException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    ServerWebInputException::class,
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

  @ExceptionHandler(ServiceException::class)
  fun handleServiceException(ex: ServiceException): ResponseEntity<ErrorResponse> {
    val errorCode = ex.errorCode
    logger.warn(
      "service exception occurred : id={} message={}",
      errorCode.id(),
      errorCode.message(),
    )
    return ResponseEntity.status(ex.errorCode.httpStatus())
      .body(
        ErrorResponse(
          message = ex.errorCode.message(),
        ),
      )
  }
}
