package kpring.core.global.controller

import kpring.core.global.dto.response.ApiResponse
import kpring.core.global.exception.ServiceException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ServiceExceptionRestController {
  private val logger = LoggerFactory.getLogger(this::class.java)

  @ExceptionHandler(ServiceException::class)
  fun handleServiceException(ex: ServiceException): ResponseEntity<Any> {
    val code = ex.errorCode
    logger.info("Service exception code : id=${code.id()} message=${code.message()}")

    return ResponseEntity.status(code.httpStatus())
      .body(ApiResponse<Any>(message = code.message()))
  }
}
