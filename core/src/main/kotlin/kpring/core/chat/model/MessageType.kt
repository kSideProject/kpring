package kpring.core.chat.model

enum class MessageType(val type: String) {
  CHAT("CHAT"),
  UPDATE("UPDATE"),
  DELETE("DELETE"),
}
