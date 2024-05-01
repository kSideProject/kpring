package kpring.core.chat.chatroom.client

import kpring.core.chat.chatroom.dto.request.CreateChatRoomRequest
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.service.annotation.PostExchange

interface ChatRoomClient {
    @PostExchange("/api/v1/chatroom")
    fun createChat(
        @Validated @RequestBody request: CreateChatRoomRequest,
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<*>
}