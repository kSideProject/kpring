package kpring.server.error

import kpring.core.global.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class ServerErrorCode(
  val id: String,
  val message: String,
  val httpStatus: HttpStatus
) : ErrorCode {

  ;

  override fun id(): String = id
  override fun message(): String = message
  override fun httpStatus(): HttpStatus = httpStatus
}
