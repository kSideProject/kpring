package kpring.chat.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val httpStatus: Int, val message: String) {
  // 400
  INVALID_TOKEN(HttpStatus.BAD_REQUEST.value(), "토큰이 유효하지 않습니다"),

  // 401
  UNAUTHORIZED_CHATROOM(HttpStatus.UNAUTHORIZED.value(), "접근이 제한된 채팅방 입니다"),

  // 404
  INVALID_TOKEN_BODY(HttpStatus.NOT_FOUND.value(), "토큰의 body가 유효하지 않습니다"),
  USERID_NOT_EXIST(HttpStatus.NOT_FOUND.value(), "토큰의 userId가 존재하지 않습니다"),
  CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 id로 chatroom을 찾을 수 없습니다"),

  // 504
  MISSING_RESPONSE(HttpStatus.GATEWAY_TIMEOUT.value(), "요청의 응답이 올바르게 돌아오지 않았습니다."),
}
