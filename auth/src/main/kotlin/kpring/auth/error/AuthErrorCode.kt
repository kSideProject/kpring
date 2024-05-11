package kpring.auth.error

import kpring.core.global.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class AuthErrorCode(
  val id: String,
  val message: String,
  val httpStatus: HttpStatus,
) : ErrorCode {

  TOKEN_NOT_VALID("AUTH_0001", "토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED),
  TOKEN_EXPIRED("AUTH_0002", "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
  ;

  override fun message(): String = this.message
  override fun id(): String = this.id
  override fun httpStatus(): HttpStatus = this.httpStatus
}

