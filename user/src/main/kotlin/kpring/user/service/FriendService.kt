package kpring.user.service

import kpring.user.dto.response.AddFriendResponse
import kpring.user.dto.response.DeleteFriendResponse
import kpring.user.dto.response.GetFriendRequestsResponse
import kpring.user.dto.response.GetFriendsResponse

interface FriendService {
  /**
   * 로그인한 사용자에게 친구신청을 한 사람들의 목록을 조회하는 메서드
   *
   * @param userId : 로그인한 사용자의 ID.
   * @return 로그인한 사용자 ID, 해당 사용자에게 친구신청을 보낸 사람들의 리스트를 GetFriendRequestsResponse 리턴
   */
  fun getFriendRequests(userId: Long): GetFriendRequestsResponse

  /**
   * 로그인한 사용자가 친구목록을 조회하는 메서드
   *
   * @param userId : 로그인한 사용자의 ID.
   * @return 로그인한 사용자 ID, 해당 사용자가 조회하고자 하는 친구목록을 담은 GetFriendsResponse 리턴
   */
  fun getFriends(userId: Long): GetFriendsResponse

  /**
   * 로그인한 사용자가 friendId를 가진 사용자에게 친구 신청을 하는 메서드
   *
   * @param userId : 친구 요청을 하고자 하는 사용자의 ID.
   * @param friendId : 친구 요청을 받는 사용자의 ID.
   * @return 로그인한 사용자가 친구 신청을 보낸 사용자의 ID를 담고 있는 AddFriendResponse 리턴
   * @throws NOT_SELF_FOLLOW
   *         : 로그인한 사용자가 스스로에게 친구신청을 시도할 때 발생하는 예외
   * @throws ALREADY_FRIEND
   *         : 친구신청을 받은 사용자와 이미 친구일 때 발생하는 예외
   */
  fun addFriend(
    userId: Long,
    friendId: Long,
  ): AddFriendResponse

  /**
   * 사용자가 친구 신청을 수락해, 두 사용자 간의 상태를 ACCEPTED 로 변경하는 메서드
   *
   * @param userId : 친구 요청을 수락하는 사용자의 ID.
   * @param friendId : 친구 요청을 보낸 사용자의 ID.
   * @return 새로 친구가 된 사용자의 ID를 담고 있는 AddFriendResponse 리턴
   * @throws FRIENDSHIP_ALREADY_EXISTS_OR_NOT_FOUND
   *         : friendId가 보낸 친구 신청이 없거나, 해당 사용자와 이미 친구일 때 발생하는 예외
   */
  fun acceptFriendRequest(
    userId: Long,
    friendId: Long,
  ): AddFriendResponse

  /***
   * 사용자가 친구와의 관계를 끊을 때 친구 상태를 삭제하는 메서드
   *
   * @param userId : 로그인한 사용자 ID.
   * @param friendId : 기존에 친구였지만 친구관계를 삭제하고자 하는 사용자 ID.
   * @return 전에 친구였던 사용자의 ID를 담고 있는 DeleteFriendResponse 리턴
   * @throws FRIEND_NOT_FOUND : 친구 중에 friendId 를 가진 사용자가 없을 경우 발생하는 예외
   */
  fun deleteFriend(
    userId: Long,
    friendId: Long,
  ): DeleteFriendResponse
}
