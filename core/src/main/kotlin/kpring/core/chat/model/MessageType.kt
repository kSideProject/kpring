package kpring.core.chat.model

enum class MessageType(val type: String) {
  ENTER("ENTER"),
  INVITATION("INVITATION"),
  CHAT("CHAT"),
  UPDATE("UPDATE"),
  DELETE("DELETE"),
  OUT("OUT"),
}
