package kpring.user.service

import kpring.core.auth.client.AuthClient
import kpring.core.auth.dto.request.CreateTokenRequest
import kpring.core.auth.dto.response.CreateTokenResponse
import kpring.user.dto.request.LoginRequest
import kpring.user.dto.request.LogoutRequest
import kpring.user.dto.response.LoginResponse
import kpring.user.exception.ErrorCode
import kpring.user.exception.ExceptionWrapper
import kpring.user.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class LoginServiceImpl(
  private val userRepository: UserRepository,
  private val authClient: AuthClient,
  private val userValidationService: UserValidationService,
) : LoginService {
  override fun login(request: LoginRequest): LoginResponse {
    val user =
      userRepository.findByEmail(request.email) ?: throw ExceptionWrapper(ErrorCode.USER_NOT_FOUND)
    userValidationService.validateUserPassword(request.password, user.password)

    val createTokenRequest = CreateTokenRequest((user.id).toString(), user.username)
    val tokenResponse = authClient.createToken(createTokenRequest)

    return handleTokenResponse(tokenResponse)
  }

  override fun logout(request: LogoutRequest) {
    TODO("Not yet implemented")
  }

  fun handleTokenResponse(tokenResponse: ResponseEntity<CreateTokenResponse>): LoginResponse {
    val body = tokenResponse.body ?: throw ExceptionWrapper(ErrorCode.NOT_ALLOWED)
    return LoginResponse(body.accessToken, body.refreshToken)
  }
}
