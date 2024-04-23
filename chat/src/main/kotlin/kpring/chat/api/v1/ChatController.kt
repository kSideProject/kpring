package kpring.chat.api.v1

import kpring.chat.service.ChatService
import kpring.core.chat.dto.request.CreateChatRequest
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ChatContoller (
    private val chatService : ChatService
){
    @PostMapping("/chat")
    fun createChat(
        @Validated @RequestBody request: CreateChatRequest
    ): ResponseEntity<*> {
        val result = chatService.createChat(request)
        return ResponseEntity.ok()
            .body(result)
    }
}