package kpring.user.service

import kpring.core.global.exception.ServiceException
import kpring.user.dto.request.CreateUserRequest
import kpring.user.dto.request.UpdateUserProfileRequest
import kpring.user.dto.response.CreateUserResponse
import kpring.user.dto.response.GetUserProfileResponse
import kpring.user.dto.response.UpdateUserProfileResponse
import kpring.user.entity.User
import kpring.user.exception.UserErrorCode
import kpring.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserServiceImpl(
  private val userRepository: UserRepository,
  private val passwordEncoder: PasswordEncoder,
  private val userValidationService: UserValidationService,
) : UserService {
  override fun getProfile(userId: Long): GetUserProfileResponse {
    val user =
      userRepository.findById(userId)
        .orElseThrow { throw ServiceException(UserErrorCode.USER_NOT_FOUND) }

    return GetUserProfileResponse(user.id, user.email, user.username)
  }

  override fun updateProfile(
    userId: Long,
    request: UpdateUserProfileRequest,
  ): UpdateUserProfileResponse {
    TODO("Not yet implemented")
  }

  override fun exitUser(userId: Long): Boolean {
    TODO("Not yet implemented")
  }

  override fun createUser(request: CreateUserRequest): CreateUserResponse {
    val password = passwordEncoder.encode(request.password)

    handleDuplicateEmail(request.email)
    userValidationService.validatePasswordMatch(request.password, request.passwordCheck)

    val user =
      userRepository.save(
        User(
          email = request.email,
          password = password,
          username = request.username,
        ),
      )

    return CreateUserResponse(user.id, user.email)
  }

  fun handleDuplicateEmail(email: String) {
    if (userRepository.existsByEmail(email)) {
      throw ServiceException(UserErrorCode.ALREADY_EXISTS_EMAIL)
    }
  }
}
