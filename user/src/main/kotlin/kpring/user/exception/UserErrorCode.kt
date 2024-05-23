package kpring.user.exception

import kpring.core.global.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class UserErrorCode(
  val httpStatus: HttpStatus,
  val id: String,
  val message: String,
) : ErrorCode {
  BAD_REQUEST(HttpStatus.BAD_REQUEST, "4000", "Invalid email"),
  ALREADY_EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "4001", "Email already exists"),
  NOT_ALLOWED(HttpStatus.FORBIDDEN, "4003", "Not allowed"), // 권한이 없는 경우

  INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED, "4010", "Incorrect password"),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "4011", "User not found"),

  EXTENSION_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "4020", "Extension is not supported"),
  ;

  override fun message(): String = this.message

  override fun id(): String = this.id

  override fun httpStatus(): HttpStatus = this.httpStatus
}
