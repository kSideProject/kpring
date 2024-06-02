package kpring.core.chat.chat.dto.response

data class ChatResponse(
  val id: String,
  val isEdited: Boolean,
  val sentAt: String,
  val content: String,
)
