package kpring.core.user.dto.request

data class LogoutRequest(
  val accessToken: String,
  val refreshToken: String,
)
