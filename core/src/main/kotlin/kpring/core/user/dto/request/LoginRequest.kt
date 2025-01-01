package kpring.core.user.dto.request

data class LoginRequest(
  val email: String,
  val password: String,
)
