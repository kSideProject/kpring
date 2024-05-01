package kpring.core.chat.chat.dto.response

import java.time.LocalDateTime

data class ChatResponse(
    val id: String,
    val isDeleted: Boolean,
    val isEdited: Boolean,
    val sentAt: LocalDateTime,
    val content: String
)