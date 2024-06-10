package kpring.user.service

import kpring.user.dto.response.DeleteFriendResponse
import kpring.user.dto.response.GetFriendsResponse
import kpring.user.dto.result.AddFriendResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FriendServiceImpl() : FriendService {
  override fun getFriends(userId: Long): GetFriendsResponse {
    TODO("Not yet implemented")
  }

  override fun addFriend(
    userId: Long,
    friendId: Long,
  ): AddFriendResponse {
    TODO("Not yet implemented")
  }

  override fun deleteFriend(
    userId: Long,
    friendId: Long,
  ): DeleteFriendResponse {
    TODO("Not yet implemented")
  }
}
