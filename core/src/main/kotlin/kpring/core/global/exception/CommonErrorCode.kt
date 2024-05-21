package kpring.core.global.exception

import org.springframework.http.HttpStatus

enum class CommonErrorCode(
  val id: String,
  val message: String,
  val httpStatus: HttpStatus,
) : ErrorCode {
  FORBIDDEN("CORE_0403", "권한이 없습니다.", HttpStatus.FORBIDDEN),
  NOT_FOUND("CORE_0404", "요청한 자원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  INTERNAL_SERVER_ERROR("CORE_0500", "서버 내부 오류", HttpStatus.INTERNAL_SERVER_ERROR),
  ;

  override fun message(): String = this.message

  override fun id(): String = this.id

  override fun httpStatus(): HttpStatus = this.httpStatus
}
