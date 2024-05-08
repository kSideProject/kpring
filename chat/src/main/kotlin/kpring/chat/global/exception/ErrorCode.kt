package kpring.chat.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val httpStatus: Int, val message: String) {
  // 400
  INVALID_TOKEN(HttpStatus.BAD_REQUEST.value(), "토큰이 유효하지 않습니다"),

  // 404
  INVALID_TOKEN_BODY(HttpStatus.NOT_FOUND.value(), "토큰의 body가 유효하지 않습니다"),

  USERID_NOT_EXIST(HttpStatus.NOT_FOUND.value(), "토큰의 userId가 존재하지 않습니다"),
}
