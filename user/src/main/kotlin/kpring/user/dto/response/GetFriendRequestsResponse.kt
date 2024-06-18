package kpring.user.dto.response

data class GetFriendRequestsResponse(
  val userId: Long,
  var friendRequests: List<GetFriendRequestResponse>,
)
