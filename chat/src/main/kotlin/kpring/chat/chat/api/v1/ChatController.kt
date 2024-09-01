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
  @PostMapping("/chat")
  fun createChat(
    @Validated @RequestBody request: CreateChatRequest,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<*> {
    val userId = authClient.getTokenInfo(token).data!!.userId

    when (request.type) {
      ChatType.Room -> chatService.createRoomChat(request, userId)
      ChatType.Server -> chatService.createServerChat(request, userId)
      else -> throw GlobalException(ErrorCode.INVALID_CHAT_TYPE)
    }

    return ResponseEntity(ApiResponse<Nothing>(status = 201), HttpStatus.CREATED)
  }

  @GetMapping("/chat")
  fun getChats(
    @RequestParam("type") type: ChatType,
    @RequestParam("id") id: String,
    @RequestParam("page") page: Int,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<*> {
    val userId = authClient.getTokenInfo(token).data!!.userId
    val result =
      when (type) {
        ChatType.Room -> chatService.getRoomChats(id, userId, page)
        ChatType.Server ->
          chatService.getServerChats(
            id,
            userId,
            page,
            serverClient.getServerList(token, GetServerCondition()).body!!.data!!,
          )
      }
    return ResponseEntity.ok().body(ApiResponse(data = result, status = 200))
  }

  @PatchMapping("/chat")
  fun updateChat(
    @Validated @RequestBody request: UpdateChatRequest,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<*> {
    val userId = authClient.getTokenInfo(token).data!!.userId
    val result =
      when (request.type) {
        ChatType.Room -> chatService.updateRoomChat(request, userId)
        ChatType.Server -> chatService.updateServerChat(request, userId)
      }
    return ResponseEntity.ok().body(ApiResponse<Nothing>(status = 200))
  }

  @DeleteMapping("/chat/{chatId}")
  fun deleteChat(
    @RequestParam("type") type: ChatType,
    @PathVariable("chatId") chatId: String,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<*> {
    val userId = authClient.getTokenInfo(token).data!!.userId
    val result =
      when (type) {
        ChatType.Room -> chatService.deleteRoomChat(chatId, userId)
        ChatType.Server -> chatService.deleteServerChat(chatId, userId)
      }
    return ResponseEntity.ok().body(ApiResponse<Nothing>(status = 200))
  }
}
