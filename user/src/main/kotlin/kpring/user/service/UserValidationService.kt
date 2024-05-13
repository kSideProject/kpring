package kpring.user.service

import kpring.core.auth.dto.response.CreateTokenResponse
import kpring.user.dto.response.LoginResponse
import kpring.user.entity.User
import kpring.user.exception.ErrorCode
import kpring.user.exception.ExceptionWrapper
import kpring.user.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserValidationService(
  private val userRepository: UserRepository,
  private val passwordEncoder: PasswordEncoder,
) {
  fun validateDuplicateEmail(email: String) {
    if (userRepository.existsByEmail(email)) {
      throw ExceptionWrapper(ErrorCode.ALREADY_EXISTS_EMAIL)
    }
  }

  fun validatePasswordMatch(
    password: String,
    passwordCheck: String,
  ) {
    if (password != passwordCheck) {
      throw ExceptionWrapper(ErrorCode.NOT_MATCH_PASSWORD)
    }
  }

  fun validateExistEmail(email: String): User {
    return userRepository.findByEmail(email) ?: throw ExceptionWrapper(ErrorCode.USER_NOT_FOUND)
  }

  fun validateUserPassword(
    rawPassword: String,
    encodedPassword: String,
  ) {
    if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
      throw ExceptionWrapper(ErrorCode.INCORRECT_PASSWORD)
    }
  }

  fun validateTokenResponse(tokenResponse: ResponseEntity<CreateTokenResponse>): LoginResponse {
    val body = tokenResponse.body ?: throw ExceptionWrapper(ErrorCode.NOT_ALLOWED)
    return LoginResponse(body.accessToken, body.refreshToken)
  }
}
