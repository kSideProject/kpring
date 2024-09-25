package kpring.core.chat.model

enum class ChatType(val type: String) {
  ROOM("Room"),
  SERVER("Server"),
  ;

  override fun toString(): String {
    return type
  }
}
