package kpring.user.service

import kpring.user.exception.ErrorCode
import kpring.user.exception.ExceptionWrapper
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserValidationService(
  private val passwordEncoder: PasswordEncoder,
) {
  fun validatePasswordMatch(
    password: String,
    passwordCheck: String,
  ) {
    if (password != passwordCheck) {
      throw ExceptionWrapper(ErrorCode.NOT_MATCH_PASSWORD)
    }
  }

  fun validateUserPassword(
    rawPassword: String,
    encodedPassword: String,
  ) {
    if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
      throw ExceptionWrapper(ErrorCode.INCORRECT_PASSWORD)
    }
  }
}
