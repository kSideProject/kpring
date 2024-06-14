package kpring.user.global

import kpring.core.auth.dto.response.TokenInfo
import kpring.core.auth.enums.TokenType
import kpring.core.global.dto.response.ApiResponse
import kpring.core.global.exception.ServiceException
import kpring.user.exception.UserErrorCode
import org.springframework.stereotype.Component

@Component
class AuthValidator() {
  fun checkIfAccessTokenAndGetUserId(validationResult: ApiResponse<TokenInfo>): String {
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
