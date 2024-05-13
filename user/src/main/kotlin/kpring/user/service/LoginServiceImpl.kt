package kpring.user.service

import kpring.core.auth.client.AuthClient
import kpring.core.auth.dto.request.CreateTokenRequest
import kpring.user.dto.request.LoginRequest
import kpring.user.dto.request.LogoutRequest
import kpring.user.dto.response.LoginResponse
import org.springframework.stereotype.Service

@Service
class LoginServiceImpl(
  private val authClient: AuthClient,
  private val userValidationService: UserValidationService,
) : LoginService {
  override fun login(request: LoginRequest): LoginResponse {
    val user = userValidationService.validateExistEmail(request.email)
    userValidationService.validateUserPassword(request.password, user.password)

    val createTokenRequest = CreateTokenRequest((user.id).toString(), user.username)
    val tokenResponse = authClient.createToken(createTokenRequest)

    return userValidationService.validateTokenResponse(tokenResponse)
  }

  override fun logout(request: LogoutRequest) {
    TODO("Not yet implemented")
  }
}
