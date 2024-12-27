package kpring.core.user.dto.response

data class LoginResponse(
  val accessToken: String,
  val refreshToken: String,
)
