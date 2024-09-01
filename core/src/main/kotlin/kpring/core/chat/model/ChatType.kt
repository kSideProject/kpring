package kpring.core.chat.model

enum class ChatType(val type: String) {
  Room("Room"),
  Server("Server"),
  ;

  override fun toString(): String {
    return type
  }
}
