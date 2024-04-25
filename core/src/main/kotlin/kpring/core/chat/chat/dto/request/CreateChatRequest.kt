package kpring.core.chat.chat.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateChatRequest (
    @field:NotNull
    val room: String,
    @field:NotBlank
    val nickname: String,
    @field:NotBlank
    val content: String
)