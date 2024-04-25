package kpring.core.chat.chatroom.dto.request

import jakarta.validation.constraints.NotNull

data class CreateChatRoomRequest (
    @field:NotNull
    var users : MutableList<String>
)