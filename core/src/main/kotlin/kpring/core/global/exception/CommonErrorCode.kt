package kpring.core.global.exception

import org.springframework.http.HttpStatus

enum class CommonErrorCode(
  val id: String,
  val message: String,
  val httpStatus: HttpStatus,
) : ErrorCode {
  INTERNAL_SERVER_ERROR("CORE_0001", "서버 내부 오류", HttpStatus.INTERNAL_SERVER_ERROR),
  ;

  override fun message(): String = this.message

  override fun id(): String = this.id

  override fun httpStatus(): HttpStatus = this.httpStatus
}
