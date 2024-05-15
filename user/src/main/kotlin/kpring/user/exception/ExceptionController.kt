package kpring.user.exception

import kpring.core.global.exception.ServiceException
import kpring.user.dto.response.FailMessageResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionController {
  private val logger = LoggerFactory.getLogger(ExceptionController::class.java)

  @ExceptionHandler(ServiceException::class)
  fun handleServiceException(e: ServiceException): ResponseEntity<FailMessageResponse> {
    val errorCode = e.errorCode
    logger.error(
      "Internal server error : id={} message={}",
      errorCode.id(),
      errorCode.message(),
    )

    val response =
      FailMessageResponse(e.errorCode.message())

    return ResponseEntity.status(e.errorCode.httpStatus())
      .body(response)
  }

  @ExceptionHandler(MethodArgumentNotValidException::class)
  fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<FailMessageResponse> {
    val response = FailMessageResponse(e.bindingResult.allErrors[0].defaultMessage)
    return ResponseEntity.badRequest().body(response)
  }

  @ExceptionHandler(IllegalArgumentException::class)
  fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<Void> {
    return ResponseEntity.badRequest().build()
  }

  @ExceptionHandler(RuntimeException::class)
  fun handleRuntimeException(e: RuntimeException): ResponseEntity<FailMessageResponse> {
    logger.error("Internal server error : ", e)
    val response = FailMessageResponse.serverError
    return ResponseEntity.internalServerError()
      .body(response)
  }
}
