package kpring.chat.chat.api.v1

import kpring.chat.chat.service.ChatService
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.auth.client.AuthClient
import kpring.core.chat.chat.dto.request.CreateRoomChatRequest
import kpring.core.chat.chat.dto.request.CreateServerChatRequest
import kpring.core.global.dto.response.ApiResponse
import kpring.core.server.client.ServerClient
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ChatController(
  private val chatService: ChatService,
  val authClient: AuthClient,
  val serverClient: ServerClient,
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

  @GetMapping("/chat/server/{serverId}")
  fun getServerChats(
    @PathVariable("serverId") serverId: String,
    @RequestParam("page") page: Int,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<*> {
    val userId = authClient.getTokenInfo(token).data!!.userId
    verifyIfUserHasJoinedServer(userId, serverId)
    val result = chatService.getChatsByServer(serverId, userId, page)
    return ResponseEntity.ok().body(ApiResponse(data = result))
  }

  fun verifyIfUserHasJoinedServer(
    userId: String,
    serverId: String,
  ) {
    val verified: Boolean = serverClient.verifyIfJoined(userId, serverId)
    if (!verified) {
      throw GlobalException(ErrorCode.MISSING_RESPONSE)
    }
  }
}
