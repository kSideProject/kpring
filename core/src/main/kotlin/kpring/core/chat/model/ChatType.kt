package kpring.core.chat.model

enum class ChatType(val type: String) {
  ROOM("ROOM"),
  SERVER("SERVER"),
  ;

  override fun toString(): String {
    return type
  }
}
