package kpring.core.chat.chat.dto.request

import jakarta.validation.constraints.NotNull
import kpring.core.chat.model.ChatType

data class DeleteChatRequest(
  @field:NotNull
  val id: String,
  @field:NotNull
  val contextId: String,
  @field:NotNull
  val type: ChatType,
)
