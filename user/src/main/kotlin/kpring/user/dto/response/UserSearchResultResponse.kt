package kpring.user.dto.response

data class UserSearchResultResponse(
  val userId: Long,
  val email: String,
  val username: String,
  val file: String?,
)
