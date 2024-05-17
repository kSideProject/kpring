package kpring.core.global.exception

import org.springframework.http.HttpStatus

enum class CommonErrorCode(
  val id: String,
  val message: String,
  val httpStatus: HttpStatus,
) : ErrorCode {
  NOT_FOUND("CORE_0001", "요청한 자원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  INTERNAL_SERVER_ERROR("CORE_0002", "서버 내부 오류", HttpStatus.INTERNAL_SERVER_ERROR),
  TIME_OUT("CORE_0003", "요청 시간이 초과되었습니다.", HttpStatus.REQUEST_TIMEOUT),
  CLIENT_ERROR("CORE_0004", "클라이언트 오류", HttpStatus.SERVICE_UNAVAILABLE),
  ;

  override fun message(): String = this.message

  override fun id(): String = this.id

  override fun httpStatus(): HttpStatus = this.httpStatus
}
