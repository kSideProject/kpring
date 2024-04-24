package kpring.chat.api.v1

import kpring.chat.service.ChatService
import kpring.core.auth.client.AuthClient
import kpring.core.chat.dto.request.CreateChatRequest
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ChatContoller(
    private val chatService: ChatService,
    val authClient: AuthClient
) {

    @PostMapping("/chat")
    fun createChat(
        @Validated @RequestBody request: CreateChatRequest, @RequestHeader("Authorization") token: String
    ): ResponseEntity<*> {
        val tokenResponse = authClient.validateToken(token)

        val result = chatService.createChat(request, tokenResponse)
        return ResponseEntity.ok().body(result)
    }
}