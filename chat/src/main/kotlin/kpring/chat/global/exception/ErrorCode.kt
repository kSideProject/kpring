package kpring.chat.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val httpStatus: Int, val message: String) {
  // 400
  INVALID_CHAT_TYPE(HttpStatus.BAD_REQUEST.value(), "잘못된 ChatType입니다."),

  // 403
  FORBIDDEN_CHATROOM(HttpStatus.FORBIDDEN.value(), "접근이 제한된 채팅방 입니다"),
  FORBIDDEN_SERVER(HttpStatus.FORBIDDEN.value(), "접근이 제한된 서버 입니다"),

  // 404
  CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 id로 chatroom을 찾을 수 없습니다"),

  // 500
  CONCURRENCY_CONFLICTION(HttpStatus.CONFLICT.value(), "동시성 충돌이 발생했습니다."),
}
