package kpring.chat.global.exception

import kpring.core.global.dto.response.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException::class)
    fun handleGlobalException(globalException: GlobalException): ApiResponse<*> {
        val errorCode = globalException.getErrorCode()
        return ApiResponse<Void>(errorCode.httpStatus, errorCode.message)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        bindingResult: BindingResult
    ): ApiResponse<List<String>> {
        val errors = bindingResult.fieldErrors.map { "${it.field} ${it.defaultMessage}" }
        return ApiResponse(HttpStatus.BAD_REQUEST.value(), "입력값이 잘못되었습니다", errors)
    }
}