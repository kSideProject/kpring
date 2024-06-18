package kpring.user.entity

enum class FriendRequestStatus {
  REQUESTED, // 친구신청을 보낸 상태
  RECEIVED, // 친구신청을 받은 상태
  ACCEPTED, // 친구신청을 수락한(친구가 된) 상태
}
