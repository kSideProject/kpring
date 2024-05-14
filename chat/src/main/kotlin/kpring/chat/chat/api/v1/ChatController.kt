package kpring.chat.chat.api.v1

import kpring.chat.chat.service.ChatService
import kpring.core.auth.client.AuthClient
import kpring.core.chat.chat.dto.request.CreateChatRequest
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ChatController(
  private val chatService: ChatService,
  val authClient: AuthClient,
) {
  @PostMapping("/chat")
  fun createChat(
    @Validated @RequestBody request: CreateChatRequest,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<*> {
    val userId = authClient.getTokenInfo(token).data!!.userId
    val result = chatService.createChat(request, userId)
    return ResponseEntity.ok().body(result)
  }

  @GetMapping("/chat/{chatRoomId}/{page}")
  fun getChatsByChatRoom(
    @PathVariable("chatRoomId") chatRoomId: String,
    @PathVariable("page") page: Int,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<*> {
    val userId = authClient.getTokenInfo(token).data!!.userId
    val result = chatService.getChatsByChatRoom(chatRoomId, userId, page)
    return ResponseEntity.ok().body(result)
  }
}
