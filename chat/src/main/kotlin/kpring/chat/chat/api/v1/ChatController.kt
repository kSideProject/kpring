package kpring.chat.chat.api.v1

import kpring.chat.chat.service.ChatService
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.auth.client.AuthClient
import kpring.core.auth.dto.response.TokenValidationResponse
import kpring.core.chat.chat.dto.request.CreateChatRequest
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ChatController(
    private val chatService: ChatService, val authClient: AuthClient
) {

    @PostMapping("/chat")
    fun createChat(
        @Validated @RequestBody request: CreateChatRequest,
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<*> {
        val userId = getUserId(authClient.validateToken(token))
        val result = chatService.createChat(request, userId)
        return ResponseEntity.ok().body(result)
    }

    @GetMapping("/chat/{chatRoomId}/{page}")
    fun getChatsByChatRoom(
        @PathVariable("chatRoomId") chatRoomId: String,
        @PathVariable("page") page: Int,
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<*> {
        val userId = getUserId(authClient.validateToken(token))
        val result = chatService.getChatsByChatRoom(chatRoomId, userId, page)
        return ResponseEntity.ok().body(result)
    }

    private fun getUserId(tokenResponse: ResponseEntity<TokenValidationResponse>): String {
        val body = tokenResponse.body ?: throw GlobalException(ErrorCode.INVALID_TOKEN_BODY)
        val userId = body.userId ?: throw GlobalException(ErrorCode.USERID_NOT_EXIST)
        if (!body.isValid) {
            throw GlobalException(ErrorCode.INVALID_TOKEN)
        }

        return userId
    }
}