package kpring.core.chat.chat.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import kpring.core.chat.model.ChatType

data class CreateChatRequest(
  @field:NotNull
  val contextId: String,
  @field:NotNull
  val type: ChatType,
  @field:NotBlank
  val content: String,
)
