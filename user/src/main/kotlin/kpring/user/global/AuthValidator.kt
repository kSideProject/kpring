package kpring.user.global

import kpring.core.auth.client.AuthClient
import kpring.core.auth.enums.TokenType
import kpring.core.global.exception.ServiceException
import kpring.user.exception.UserErrorCode
import org.springframework.stereotype.Component

@Component
class AuthValidator(
  private val authClient: AuthClient,
) {
  fun checkIfAccessTokenAndGetUserId(token: String): String {
    val validationResult = authClient.getTokenInfo(token)
    if (validationResult.data!!.type != TokenType.ACCESS) {
      throw ServiceException(UserErrorCode.BAD_REQUEST)
    }

    return validationResult.data!!.userId
  }

  fun checkIfUserIsSelf(
    userId: String,
    validatedUserId: String,
  ) {
    if (userId != validatedUserId) {
      throw ServiceException(UserErrorCode.NOT_ALLOWED)
    }
  }
}
