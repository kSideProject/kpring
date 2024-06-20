package kpring.user.exception

import kpring.core.global.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class UserErrorCode(
  val httpStatus: HttpStatus,
  val id: String,
  val message: String,
) : ErrorCode {
  BAD_REQUEST(HttpStatus.BAD_REQUEST, "4000", "이메일 형식이 올바르지 않습니다."),
  ALREADY_EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "4001", "이미 존재하는 이메일입니다."),
  NOT_ALLOWED(HttpStatus.FORBIDDEN, "4003", "권한이 없는 사용자입니다."), // 권한이 없는 경우

  INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED, "4010", "비밀번호가 올바르지 않습니다."),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "4011", "사용자를 찾을 수 없습니다."),

  EXTENSION_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "4020", "지원되지 않는 미디어 유형입니다."),

  ALREADY_FRIEND(HttpStatus.BAD_REQUEST, "4030", "이미 친구입니다."),
  NOT_SELF_FOLLOW(HttpStatus.BAD_REQUEST, "4031", "자기자신에게 친구요청을 보낼 수 없습니다"),
  FRIENDSHIP_ALREADY_EXISTS_OR_NOT_FOUND(
    HttpStatus.BAD_REQUEST,
    "4032",
    "해당하는 친구신청이 없거나 이미 친구입니다.",
  ),
  FRIEND_NOT_FOUND(HttpStatus.BAD_REQUEST, "4033", "해당하는 친구가 없습니다."),
  ;

  override fun message(): String = this.message

  override fun id(): String = this.id

  override fun httpStatus(): HttpStatus = this.httpStatus
}
