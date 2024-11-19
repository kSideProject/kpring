package kpring.core.chat.chat.dto.response

import kpring.core.chat.model.MessageType

data class ChatResponse(
  val id: String,
  val sender: String,
  val messageType: MessageType,
  val eventType: EventType,
  val isEdited: Boolean,
  val sentAt: String,
  val content: String,
)
