package kpring.core.user.dto.response

data class GetFriendsResponse(
  val userId: Long,
  val friends: List<GetFriendResponse>,
)
