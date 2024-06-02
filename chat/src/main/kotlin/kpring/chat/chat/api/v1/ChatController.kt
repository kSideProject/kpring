package kpring.chat.chat.api.v1

import kpring.chat.chat.service.ChatService
import kpring.core.auth.client.AuthClient
import kpring.core.chat.chat.dto.request.CreateChatRequest
import kpring.core.chat.chat.dto.response.ChatResponse
import kpring.core.global.dto.response.ApiResponse
import kpring.core.server.client.ServerClient
import kpring.core.server.dto.request.GetServerCondition
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
  @PostMapping("/chat")
  fun createChat(
    @Validated @RequestBody request: CreateChatRequest,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<*> {
    val userId = authClient.getTokenInfo(token).data!!.userId
    val result = chatService.createChat(request, userId)
    return ResponseEntity.ok().body(result)
  }

  @GetMapping("/chat")
  fun getChats(
    @RequestParam("type") type: String,
    @RequestParam("id") id: String,
    @RequestParam("page") page: Int,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<*> {
    val userId = authClient.getTokenInfo(token).data!!.userId
    var result: List<ChatResponse>? = null

    if (type.equals("Room")) {
      result = chatService.getRoomChats(id, userId, page)
    } else if (type.equals("Server")) {
      val serverList = serverClient.getServerList(token, GetServerCondition()).body!!.data!!
      result = chatService.getServerChats(id, userId, page, serverList)
    }

    return ResponseEntity.ok().body(ApiResponse(data = result, status = 200))
  }
}
