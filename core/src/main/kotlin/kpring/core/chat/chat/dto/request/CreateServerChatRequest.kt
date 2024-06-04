package kpring.core.chat.chat.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

class CreateServerChatRequest(
  @field:NotNull val server: String,
  @field:NotBlank val content: String,
)
