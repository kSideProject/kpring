package kpring.core.global.exception

import org.springframework.http.HttpStatus

class DetailErrorCode(
  val message: String,
  val httpStatus: HttpStatus,
  val id: String = "custom error",
) : ErrorCode {
  override fun message(): String = message

  override fun id(): String = id

  override fun httpStatus(): HttpStatus = httpStatus
}
