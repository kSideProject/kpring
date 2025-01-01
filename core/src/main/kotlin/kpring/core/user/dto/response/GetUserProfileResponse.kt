package kpring.core.user.dto.response

data class GetUserProfileResponse(
  val userId: Long,
  val email: String,
  val username: String,
  val filename: String?,
)
