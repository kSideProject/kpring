package kpring.chat.chat.api.v1

import kpring.chat.chat.service.ChatService
import kpring.core.auth.client.AuthClient
import kpring.core.chat.chat.dto.request.CreateRoomChatRequest
import kpring.core.chat.chat.dto.request.CreateServerChatRequest
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ChatController(
  private val chatService: ChatService,
  val authClient: AuthClient,
) {
  @PostMapping("/chat/chatroom")
  fun createRoomChat(
    @Validated @RequestBody request: CreateRoomChatRequest,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<*> {
    val userId = authClient.getTokenInfo(token).data!!.userId
    val result = chatService.createRoomChat(request, userId)
    return ResponseEntity.ok().body(result)
  }

  @PostMapping("/chat/server")
  fun createServerChat(
    @Validated @RequestBody request: CreateServerChatRequest,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<*> {
    val userId = authClient.getTokenInfo(token).data!!.userId
    val result = chatService.createServerChat(request, userId)
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
