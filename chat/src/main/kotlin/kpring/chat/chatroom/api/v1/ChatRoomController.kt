package kpring.chat.chatroom.api.v1

import kpring.chat.chatroom.service.ChatRoomService
import kpring.core.auth.client.AuthClient
import kpring.core.chat.chatroom.dto.request.CreateChatRoomRequest
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ChatRoomController(
  private val chatRoomService: ChatRoomService,
  private val authClient: AuthClient,
) {
  @PostMapping("/chatroom")
  fun createChatRoom(
    @Validated @RequestBody request: CreateChatRoomRequest,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<*> {
    val userId = authClient.getTokenInfo(token).data!!.userId

    val result = chatRoomService.createChatRoom(request, userId)
    return ResponseEntity.ok().body(result)
  }

  @PatchMapping("/chatroom/exit/{chatRoomId}")
  fun exitChatRoom(
    @PathVariable("chatRoomId") chatRoomId: String,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<*> {
    val userId = authClient.getTokenInfo(token).data!!.userId
    val result = chatRoomService.exitChatRoom(chatRoomId, userId)
    return ResponseEntity.ok().body(result)
  }

  @PatchMapping("/chatroom/{chatRoomId}/invite/{userId}")
  fun inviteToChatRoomByUserId(
    @PathVariable("userId") userId: String,
    @PathVariable("chatRoomId") chatRoomId: String,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<*> {
    val inviterId = authClient.getTokenInfo(token).data!!.userId
    val result = chatRoomService.inviteToChatRoomByUserId(userId, inviterId, chatRoomId)
    return ResponseEntity.ok().body(result)
  }
}
