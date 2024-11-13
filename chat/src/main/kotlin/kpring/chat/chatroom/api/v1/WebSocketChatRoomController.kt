package kpring.chat.chatroom.api.v1

import kpring.chat.chatroom.service.ChatRoomService
import kpring.core.chat.chatroom.dto.request.CreateChatRoomRequest
import kpring.core.chat.chatroom.dto.request.ExpelChatRoomRequest
import kpring.core.global.dto.response.ApiResponse
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import java.security.Principal

@Controller
@RequiredArgsConstructor
class WebSocketChatRoomController(
  private val chatRoomService: ChatRoomService,
  private val simpMessagingTemplate: SimpMessagingTemplate,
) {
  private val logger: Logger = LoggerFactory.getLogger(WebSocketChatRoomController::class.java)

  @MessageMapping("/chatroom/create")
  fun createChatRoom(
    @Payload @Validated request: CreateChatRoomRequest,
    principal: Principal,
  ) {
    val userId = principal.name
    val result = chatRoomService.createChatRoom(request, userId)
    simpMessagingTemplate.convertAndSend("/topic/chatroom/${result.chatRoomId}", ApiResponse(status = 200, data = result.chatResponse))
  }

  @MessageMapping("/chatroom/exit")
  fun exitChatRoom(
    @Payload chatRoomId: String,
    principal: Principal,
  ) {
    val userId = principal.name
    val result = chatRoomService.exitChatRoom(chatRoomId, userId)
    simpMessagingTemplate.convertAndSend("/topic/chatroom/${result.chatRoomId}", ApiResponse(status = 200, data = result.chatResponse))
  }

  @MessageMapping("/chatroom/join")
  fun joinChatRoom(
    @Payload code: String,
    principal: Principal,
  ) {
    val userId = principal.name
    val result = chatRoomService.joinChatRoom(code, userId)
    simpMessagingTemplate.convertAndSend("/topic/chatroom/${result.chatRoomId}", ApiResponse(status = 200, data = result.chatResponse))
  }

  @MessageMapping("/chatroom/expel")
  fun expelFromChatRoom(
    @Payload expelChatRoomRequest: ExpelChatRoomRequest,
    principal: Principal,
  ) {
    val userId = principal.name
    val result = chatRoomService.expelFromChatRoom(expelChatRoomRequest, userId)
    simpMessagingTemplate.convertAndSend("/topic/chatroom/${result.chatRoomId}", ApiResponse(status = 200, data = result.chatResponse))
  }
}
