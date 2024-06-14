package kpring.user.dto.response

data class GetFriendRequestsResponse(
  val userId: Long,
  var friendRequests: MutableList<GetFriendRequestResponse>,
)
