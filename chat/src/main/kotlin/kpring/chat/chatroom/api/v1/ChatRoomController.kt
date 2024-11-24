package kpring.chat.chatroom.api.v1

import kpring.chat.chatroom.service.ChatRoomService
import kpring.core.auth.client.AuthClient
import kpring.core.chat.chatroom.dto.request.CreateChatRoomRequest
import kpring.core.global.dto.response.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ChatRoomController(
  private val chatRoomService: ChatRoomService,
  private val authClient: AuthClient,
) {
  @Deprecated("WebSocketChatRoomController의 createChatRoom을 이용")
  @PostMapping("/chatroom")
  fun createChatRoom(
    @Validated @RequestBody request: CreateChatRoomRequest,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<*> {
    val userId = authClient.getTokenInfo(token).data!!.userId

    val result = chatRoomService.createChatRoom(request, userId)
    return ResponseEntity.ok().body(result)
  }

  @Deprecated("WebSocketChatRoomController의 exitChatRoom을 이용")
  @PatchMapping("/chatroom/exit/{chatRoomId}")
  fun exitChatRoom(
    @PathVariable("chatRoomId") chatRoomId: String,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<*> {
    val userId = authClient.getTokenInfo(token).data!!.userId

    val result = chatRoomService.exitChatRoom(chatRoomId, userId)
    return ResponseEntity.ok().body(result)
  }

  @GetMapping("/chatroom/{chatRoomId}/invite")
  fun getChatRoomInvitation(
    @PathVariable("chatRoomId") chatRoomId: String,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<*> {
    val userId = authClient.getTokenInfo(token).data!!.userId
    val result = chatRoomService.getChatRoomInvitation(chatRoomId, userId)
    return ResponseEntity.ok().body(ApiResponse(data = result))
  }

  @Deprecated("WebSocketChatRoomController의 joinChatRoom을 이용")
  @PatchMapping("/chatroom/{code}/join")
  fun joinChatRoom(
    @PathVariable("code") code: String,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<*> {
    val userId = authClient.getTokenInfo(token).data!!.userId
    val result = chatRoomService.joinChatRoom(code, userId)
    return ResponseEntity.ok().body(ApiResponse<Nothing>(status = 200))
  }
}
