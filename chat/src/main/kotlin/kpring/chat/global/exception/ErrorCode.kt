package kpring.chat.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val httpStatus: Int, val message: String) {
  USER_ALREADY_EXIST_IN_CHATROOM(HttpStatus.BAD_REQUEST.value(), "이미 참여한 사용자 입니다"),

  // 401
  UNAUTHORIZED_CHATROOM(HttpStatus.UNAUTHORIZED.value(), "접근이 제한된 채팅방 입니다"),

  // 404
  CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 id로 chatroom을 찾을 수 없습니다"),

  //409
  LOCKING_FAILURE(HttpStatus.CONFLICT.value(), "충돌이 발생했습니다."),
}
