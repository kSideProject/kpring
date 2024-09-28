package kpring.user.dto.response

import java.nio.file.Path

data class GetFriendResponse(
  val friendId: Long,
  val username: String,
  val email: String,
  val imagePath: Path?,
)
