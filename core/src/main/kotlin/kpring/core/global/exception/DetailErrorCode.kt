package kpring.core.global.exception

import org.springframework.http.HttpStatus

class DetailErrorCode(
  val message: String,
  val id: String = "custom error",
  val httpStatus: HttpStatus,
) : ErrorCode {
  override fun message(): String = message

  override fun id(): String = id

  override fun httpStatus(): HttpStatus = httpStatus
}
