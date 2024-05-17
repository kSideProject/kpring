package kpring.core.server.dto.request

data class AddUserAtServerRequest(
  val userId: String,
  val userName: String,
  val profileImage: String,
)
