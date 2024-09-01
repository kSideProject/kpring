package kpring.core.chat.model

enum class MessageType(val type: String) {
  ENTER("ENTER"),
  INVITATION("INVITATION"),
  CHAT("CHAT"),
  TALK("TALK"),
  OUT("OUT"),
}
