package kpring.core.chat.chat.dto.request

import jakarta.validation.constraints.NotNull
import kpring.core.chat.model.ChatType

data class GetChatsRequest(
  @field:NotNull
  val contextId: String,
  @field:NotNull
  val type: ChatType,
  @field:NotNull
  val page: Int,
)
