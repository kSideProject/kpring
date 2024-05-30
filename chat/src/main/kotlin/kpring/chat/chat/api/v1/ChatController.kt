package kpring.chat.chat.api.v1

import kpring.chat.chat.service.ChatService
import kpring.core.auth.client.AuthClient
import kpring.core.chat.chat.dto.request.ChatType
import kpring.core.chat.chat.dto.request.CreateChatRequest
import kpring.core.global.dto.response.ApiResponse
import org.springframework.http.HttpStatus
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

    if (request.type == ChatType.Room) {
      chatService.createRoomChat(request, userId)
    } else {
      chatService.createServerChat(request, userId)
    }

    return ResponseEntity(ApiResponse(data = null, status = 201), HttpStatus.CREATED)
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
