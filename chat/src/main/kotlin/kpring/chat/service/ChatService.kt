package kpring.chat.service

import kpring.chat.exception.ErrorCode
import kpring.chat.exception.GlobalException
import kpring.chat.model.Chat
import kpring.chat.repository.ChatRepository
import kpring.core.auth.dto.response.TokenValidationResponse
import kpring.core.chat.dto.request.CreateChatRequest
import kpring.core.chat.dto.response.CreateChatResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatRepository: ChatRepository
) {
    /*
     business logic
     */
    fun createChat(
        request: CreateChatRequest,
        tokenResponse: ResponseEntity<TokenValidationResponse>
    ): CreateChatResponse {

        val user = tokenResponse.body?.userId ?: throw GlobalException(ErrorCode.INVALID_TOKEN_BODY)
        val chat = chatRepository.save(
            Chat(
                userId = user, roomId = request.room, nickname = request.nickname, content = request.content
            )
        )
        return CreateChatResponse(
            200, "success"
        )
    }
}