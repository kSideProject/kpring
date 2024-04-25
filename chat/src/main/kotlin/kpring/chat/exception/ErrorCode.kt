package kpring.chat.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val httpStatus: Int, val message: String) {

    //400
    INVALID_TOKEN_BODY(HttpStatus.NOT_FOUND.value(), "토큰의 body가 유효하지 않습니다"),

}