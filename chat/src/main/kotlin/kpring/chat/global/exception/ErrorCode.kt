package kpring.chat.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val httpStatus: Int, val message: String) {
  // 400
  INVALID_CHAT_TYPE(HttpStatus.BAD_REQUEST.value(), "잘못된 ChatType입니다."),
  MISSING_CONTEXTID(HttpStatus.BAD_REQUEST.value(), "채팅방ID 혹은 서버ID가 누락되었습니다."),
  MISSING_CONTEXT(HttpStatus.BAD_REQUEST.value(), "ChatType이 누락되었습니다."),
  INVALID_TOKEN(HttpStatus.BAD_REQUEST.value(), "인증 토큰이 유효하지 않습니다."),
  INVALID_CONTEXT(HttpStatus.BAD_REQUEST.value(), "ChatType이 유효하지 않습니다."),

  // 401
  MISSING_TOKEN(HttpStatus.UNAUTHORIZED.value(), "인증 토큰이 누락되었습니다."),

  // 403
  FORBIDDEN_CHATROOM(HttpStatus.FORBIDDEN.value(), "채팅방 권한이 없습니다"),
  FORBIDDEN_SERVER(HttpStatus.FORBIDDEN.value(), "서버 권한이 없습니다"),
  FORBIDDEN_CHAT(HttpStatus.FORBIDDEN.value(), "채팅 권한이 없습니다"),

  // 404
  CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 id로 chatroom을 찾을 수 없습니다"),
  CHAT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 id로 채팅을 찾을 수 없습니다"),

  // 410
  EXPIRED_INVITATION(HttpStatus.GONE.value(), "만료된 Invitation입니다."),

  // 500
  INVITATION_LINK_SAVE_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invitation Code가 저장되지 않았습니다"),
  SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server module에서 에러가 발생했습니다"),
}
