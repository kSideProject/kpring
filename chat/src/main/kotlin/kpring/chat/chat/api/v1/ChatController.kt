package kpring.chat.chat.api.v1

import kpring.chat.chat.service.ChatService
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.auth.client.AuthClient
import kpring.core.chat.chat.dto.request.CreateChatRequest
import kpring.core.chat.chat.dto.request.UpdateChatRequest
import kpring.core.chat.model.ChatType
import kpring.core.global.dto.response.ApiResponse
import kpring.core.server.client.ServerClient
import kpring.core.server.dto.request.GetServerCondition
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ChatController(
  private val chatService: ChatService,
  private val authClient: AuthClient,
  private val serverClient: ServerClient,
) {
  @Deprecated("WebSocketChatController를 대신 사용")
  @PostMapping("/chat")
  fun createChat(
    @Validated @RequestBody request: CreateChatRequest,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<*> {
    val userId = authClient.getTokenInfo(token).data!!.userId

    when (request.type) {
      ChatType.ROOM -> chatService.createRoomChat(request, userId)
      ChatType.SERVER ->
        chatService.createServerChat(
          request,
          userId,
          serverClient.getServerList(token, GetServerCondition()).body!!.data!!,
        )
      else -> throw GlobalException(ErrorCode.INVALID_CHAT_TYPE)
    }

    return ResponseEntity(ApiResponse<Nothing>(status = 201), HttpStatus.CREATED)
  }

  @Deprecated("WebSocketChatController를 대신 사용")
  @GetMapping("/chat")
  fun getChats(
    @RequestParam("type") type: ChatType,
    @RequestParam("id") id: String,
    @RequestParam("page") page: Int,
    @RequestParam("size") size: Int,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<*> {
    val userId = authClient.getTokenInfo(token).data!!.userId
    val result =
      when (type) {
        ChatType.ROOM -> chatService.getRoomChats(id, userId, page, size)
        ChatType.SERVER ->
          chatService.getServerChats(
            id,
            userId,
            page,
            size,
            serverClient.getServerList(token, GetServerCondition()).body!!.data!!,
          )else -> {
          throw GlobalException(ErrorCode.INVALID_CHAT_TYPE)
        }
      }
    return ResponseEntity.ok().body(ApiResponse(data = result, status = 200))
  }

  @Deprecated("WebSocketChatController를 대신 사용")
  @PatchMapping("/chat")
  fun updateChat(
    @Validated @RequestBody request: UpdateChatRequest,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<*> {
    val userId = authClient.getTokenInfo(token).data!!.userId
    val result = chatService.updateChat(request, userId)
    return ResponseEntity.ok().body(ApiResponse<Nothing>(status = 200))
  }

  @Deprecated("WebSocketChatController를 대신 사용")
  @DeleteMapping("/chat/{chatId}")
  fun deleteChat(
    @PathVariable("chatId") chatId: String,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<*> {
    val userId = authClient.getTokenInfo(token).data!!.userId
    val result = chatService.deleteChat(chatId, userId)
    return ResponseEntity.ok().body(ApiResponse<Nothing>(status = 200))
  }
}
