package kpring.chat.chatroom.dto

import kpring.core.chat.chat.dto.response.ChatResponse

data class ChatWrapper(
  val chatRoomId: String,
  val chatResponse: ChatResponse,
)
