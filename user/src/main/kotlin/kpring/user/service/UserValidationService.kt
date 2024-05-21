package kpring.user.service

import kpring.core.global.exception.ServiceException
import kpring.user.exception.UserErrorCode
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserValidationService(
  private val passwordEncoder: PasswordEncoder,
) {
  fun validateUserPassword(
    rawPassword: String,
    encodedPassword: String,
  ) {
    if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
      throw ServiceException(UserErrorCode.INCORRECT_PASSWORD)
    }
  }
}
