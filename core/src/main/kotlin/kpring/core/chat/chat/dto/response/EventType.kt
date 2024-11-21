package kpring.core.chat.chat.dto.response

enum class EventType(val type: String) {
  ENTER("ENTER"),
  INVITATION("INVITATION"),
  CREATED("CREATED"),
  CHAT("CHAT"),
  EXIT("EXIT"),
  EXPEL("EXPEL"),
}
