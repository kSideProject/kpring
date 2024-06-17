package kpring.chat.chatroom.dto

data class InvitationInfo(
  val userId: String,
  val chatRoomId: String,
  val code: String,
)
