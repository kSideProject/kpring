package kpring.chat.chatroom.dto

data class Lock(
  val lockId: String,
  val owner: String,
  val acquired: Boolean,
)
