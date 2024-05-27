package kpring.server.error

import kpring.core.global.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class ServerErrorCode(
  val id: String,
  val message: String,
  val httpStatus: HttpStatus,
) : ErrorCode {
  USER_NOT_INVITED("SERVER_001", "유저가 초대되지 않았습니다.", HttpStatus.FORBIDDEN),
  ALREADY_REGISTERED_USER("SERVER_002", "이미 등록된 유저입니다.", HttpStatus.BAD_REQUEST),
  ;

  override fun id(): String = id

  override fun message(): String = message

  override fun httpStatus(): HttpStatus = httpStatus
}
