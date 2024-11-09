package kpring.chat.chatroom.model

enum class EventType(val type: String) {
  ENTER("ENTER"),
  INVITATION("INVITATION"),
  CREATED("CREATED"),
  CHAT("CHAT"),
  EXIT("EXIT"),
}
