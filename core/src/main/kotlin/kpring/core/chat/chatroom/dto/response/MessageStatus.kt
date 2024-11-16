package kpring.core.chat.chatroom.dto.response

enum class MessageStatus(val code: Int, val description: String) {
  DISCONNECT(1000, "The user should disconnect"),
}
