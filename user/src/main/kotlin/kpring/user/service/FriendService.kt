package kpring.user.service

import kpring.user.dto.response.AddFriendResponse
import kpring.user.dto.response.DeleteFriendResponse
import kpring.user.dto.response.GetFriendsResponse

interface FriendService {
  fun getFriends(userId: Long): GetFriendsResponse

  fun addFriend(
    userId: Long,
    friendId: Long,
  ): AddFriendResponse

  fun deleteFriend(
    userId: Long,
    friendId: Long,
  ): DeleteFriendResponse
}
