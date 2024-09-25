package kpring.core.chat.chat.dto.response

data class ChatListResponse(
  val chats: List<ChatResponse>,
  val currentPage: Int,
  val totalPages: Int,
)
