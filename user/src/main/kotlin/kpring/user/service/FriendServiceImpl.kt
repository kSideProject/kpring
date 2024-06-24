package kpring.user.service

import kpring.core.global.exception.ServiceException
import kpring.user.dto.response.*
import kpring.user.entity.Friend
import kpring.user.entity.FriendRequestStatus
import kpring.user.entity.User
import kpring.user.exception.UserErrorCode
import kpring.user.repository.FriendRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
@Transactional
class FriendServiceImpl(
  private val userServiceImpl: UserServiceImpl,
  private val friendRepository: FriendRepository,
) : FriendService {
  override fun getFriendRequests(userId: Long): GetFriendRequestsResponse {
    val friendRelations: List<Friend> =
      friendRepository.findAllByUserIdAndRequestStatus(userId, FriendRequestStatus.RECEIVED)

    val friendRequests =
      friendRelations.stream().map { friendRelation ->
        val friend = friendRelation.friend
        GetFriendRequestResponse(friend.id!!, friend.username)
      }.collect(Collectors.toList())

    return GetFriendRequestsResponse(userId, friendRequests)
  }

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
    user.requestFriend(friend)
    friend.receiveFriendRequest(user)

    return AddFriendResponse(friend.id!!)
  }

  override fun acceptFriendRequest(
    userId: Long,
    friendId: Long,
  ): AddFriendResponse {
    val receivedFriend = getPendingFriendship(userId, friendId)
    val requestedFriend = getPendingFriendship(friendId, userId)

    receivedFriend.updateRequestStatus(FriendRequestStatus.ACCEPTED)
    requestedFriend.updateRequestStatus(FriendRequestStatus.ACCEPTED)

    return AddFriendResponse(friendId)
  }

  override fun deleteFriend(
    userId: Long,
    friendId: Long,
  ): DeleteFriendResponse {
    val user = userServiceImpl.getUser(userId)
    userServiceImpl.getUser(friendId)

    val userFriendRelation = findAcceptedFriendship(userId, friendId)
    user.removeFriendRelation(userFriendRelation)

    return DeleteFriendResponse(friendId)
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

  private fun findFriendship(
    userId: Long,
    friendId: Long,
  ): Friend {
    val friendship =
      friendRepository.findByUserIdAndFriendId(userId, friendId)
        ?: throw ServiceException(UserErrorCode.FRIENDSHIP_NOT_FOUND)
    return friendship
  }

  private fun checkNotAcceptedFriendship(friendship: Friend) {
    if (friendship.requestStatus == FriendRequestStatus.ACCEPTED) {
      throw ServiceException(UserErrorCode.FRIENDSHIP_ALREADY_EXISTS)
    }
  }

  private fun getPendingFriendship(
    userId: Long,
    friendId: Long,
  ): Friend {
    val friendship = findFriendship(userId, friendId)
    checkNotAcceptedFriendship(friendship)
    return friendship
  }

  private fun findAcceptedFriendship(
    userId: Long,
    friendId: Long,
  ): Friend {
    return friendRepository
      .findByUserIdAndFriendIdAndRequestStatus(userId, friendId, FriendRequestStatus.ACCEPTED)
      ?: throw ServiceException(UserErrorCode.FRIEND_NOT_FOUND)
  }
}
