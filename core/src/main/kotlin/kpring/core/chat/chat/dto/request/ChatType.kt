package kpring.core.chat.chat.dto.request

enum class ChatType(val type: String) {
  Room("Room"),
  Server("Server"),
  ;

  override fun toString(): String  {
    return type
  }
}
