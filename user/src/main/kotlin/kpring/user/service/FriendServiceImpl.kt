package kpring.user.service

import kpring.core.global.exception.ServiceException
import kpring.user.dto.response.DeleteFriendResponse
import kpring.user.dto.response.GetFriendsResponse
import kpring.user.dto.result.AddFriendResponse
import kpring.user.entity.FriendRequestStatus
import kpring.user.entity.User
import kpring.user.exception.UserErrorCode
import kpring.user.repository.FriendRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FriendServiceImpl(
  private val userServiceImpl: UserServiceImpl,
  private val friendRepository: FriendRepository,
) : FriendService {
  override fun getFriends(userId: Long): GetFriendsResponse {
    TODO("Not yet implemented")
  }

  override fun addFriend(
    userId: Long,
    friendId: Long,
  ): AddFriendResponse {
    val user = userServiceImpl.getUser(userId)
    val friend = userServiceImpl.getUser(friendId)

    checkSelfFriend(user, friend)
    checkFriendRelationExists(userId, friendId)
    user.addFriendRelation(friend, FriendRequestStatus.REQUESTED)
    friend.addFriendRelation(user, FriendRequestStatus.RECEIVED)

    return AddFriendResponse(friend.id!!)
  }

  override fun deleteFriend(
    userId: Long,
    friendId: Long,
  ): DeleteFriendResponse {
    TODO("Not yet implemented")
  }

  fun checkSelfFriend(
    user: User,
    friend: User,
  ) {
    if (user == friend) {
      throw ServiceException(UserErrorCode.NOT_SELF_FOLLOW)
    }
  }

  fun checkFriendRelationExists(
    userId: Long,
    friendId: Long,
  ) {
    if (friendRepository.existsByUserIdAndFriendId(userId, friendId)) {
      throw ServiceException(UserErrorCode.ALREADY_FRIEND)
    }
  }
}
