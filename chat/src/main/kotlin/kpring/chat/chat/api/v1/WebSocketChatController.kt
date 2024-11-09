package kpring.chat.chat.api.v1

import kpring.chat.chat.service.ChatService
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.chat.chat.dto.request.CreateChatRequest
import kpring.core.chat.chat.dto.request.DeleteChatRequest
import kpring.core.chat.chat.dto.request.GetChatsRequest
import kpring.core.chat.chat.dto.request.UpdateChatRequest
import kpring.core.chat.model.ChatType
import kpring.core.server.client.ServerClient
import kpring.core.server.dto.request.GetServerCondition
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import java.security.Principal

@Controller
@RequiredArgsConstructor
class WebSocketChatController(
  private val chatService: ChatService,
  private val serverClient: ServerClient,
  private val simpMessagingTemplate: SimpMessagingTemplate,
) {
  private val logger: Logger = LoggerFactory.getLogger(WebSocketChatController::class.java)

  @MessageMapping("/chat/create")
  fun createChat(
    @Payload @Validated request: CreateChatRequest,
    principal: Principal,
    headerAccessor: SimpMessageHeaderAccessor,
  ) {
    val token = headerAccessor.getFirstNativeHeader("Authorization") ?: throw GlobalException(ErrorCode.MISSING_TOKEN)
    val userId = principal.name
    val contextId = request.contextId

    val result =
      when (request.type) {
        ChatType.ROOM -> chatService.createRoomChat(request, userId)
        ChatType.SERVER ->
          chatService.createServerChat(
            request,
            userId,
            serverClient.getServerList(token, GetServerCondition()).body!!.data!!,
          )
      }
    simpMessagingTemplate.convertAndSend("/topic/chatroom/$contextId", result)
  }

  @MessageMapping("/chat/update")
  fun updateChat(
    @Payload @Validated request: UpdateChatRequest,
    principal: Principal,
  ) {
    val userId = principal.name
    val contextId = request.contextId

    val result = chatService.updateChat(request, userId)
    simpMessagingTemplate.convertAndSend("/topic/chatroom/$contextId", result)
  }

  @MessageMapping("/chat/delete")
  fun deleteChat(
    @Payload @Validated request: DeleteChatRequest,
    principal: Principal,
  ) {
    val userId = principal.name
    val chatId = request.id
    val contextId = request.contextId

    val result = chatService.deleteChat(chatId, userId)
    simpMessagingTemplate.convertAndSend("/topic/chatroom/$contextId", result)
  }

  @MessageMapping("/chat")
  fun getChats(
    @Payload @Validated request: GetChatsRequest,
    principal: Principal,
    headerAccessor: SimpMessageHeaderAccessor,
  ) {
    val token = headerAccessor.getFirstNativeHeader("Authorization") ?: throw GlobalException(ErrorCode.MISSING_TOKEN)
    val userId = principal.name
    val type = request.type
    val contextId = request.contextId
    val page = request.page
    val size = request.size

    val result =
      when (type) {
        ChatType.ROOM -> chatService.getRoomChats(contextId, userId, page, size)
        ChatType.SERVER ->
          chatService.getServerChats(
            contextId,
            userId,
            page,
            size,
            serverClient.getServerList(token, GetServerCondition()).body!!.data!!,
          )
      }
    logger.info("Chat result: $result")
    simpMessagingTemplate.convertAndSend("/topic/chatroom/$contextId", result)
  }
}
