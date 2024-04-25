package kpring.chat.chat.api.v1

import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.chat.chat.service.ChatService
import kpring.core.auth.client.AuthClient
import kpring.core.chat.chat.dto.request.CreateChatRequest
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ChatContoller(
    private val chatService: ChatService, val authClient: AuthClient
) {

    @PostMapping("/chat")
    fun createChat(
        @Validated @RequestBody request: CreateChatRequest, @RequestHeader("Authorization") token: String
    ): ResponseEntity<*> {
        val tokenResponse = authClient.validateToken(token)
        val userId = tokenResponse.body?.userId ?: throw GlobalException(ErrorCode.INVALID_TOKEN_BODY)

        val result = chatService.createChat(request, userId)
        return ResponseEntity.ok().body(result)
    }

//    @GetMapping("/chat")
//    fun getChats(
//        @Validated @RequestParam roomId: String,
//        @RequestHeader("Authorization") token: String
//    ): ResponseEntity<*> {
//        val tokenResponse = authClient.validateToken(token)
//        val userId = tokenResponse.body?.userId ?: throw GlobalException(ErrorCode.INVALID_TOKEN_BODY)
//
//        val result = chatService.getChats(roomId, userId)
//        return ResponseEntity.ok().body(result)
//    }
}