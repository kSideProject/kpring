package kpring.core.global.exception

import org.springframework.http.HttpStatus

interface ErrorCode {
  fun message(): String
  fun id(): String
  fun httpStatus(): HttpStatus
}
