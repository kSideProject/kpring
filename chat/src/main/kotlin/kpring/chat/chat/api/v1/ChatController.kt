package kpring.chat.chat.api.v1

import kpring.chat.chat.service.ChatService
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.auth.client.AuthClient
import kpring.core.chat.chat.dto.request.CreateChatRequest
import kpring.core.global.dto.response.ApiResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ChatController(
    private val chatService: ChatService, val authClient: AuthClient,
) {

    @PostMapping("/chat")
    fun createChat(
        @Validated @RequestBody request: CreateChatRequest, @RequestHeader("Authorization") token: String,
    ): ResponseEntity<*> {
        val tokenResponse = authClient.validateToken(token)
        val body = tokenResponse.body ?: throw GlobalException(ErrorCode.INVALID_TOKEN_BODY)
        val userId = body.userId ?: throw GlobalException(ErrorCode.USERID_NOT_EXIST)
        val isValid = body.isValid
        if (!isValid) {
            throw GlobalException(ErrorCode.INVALID_TOKEN)
        }

        val result = chatService.createChat(request, userId)
        return ResponseEntity.ok().body(result)
    }

    @GetMapping("/chat")
    fun getChatList(
        @RequestHeader("Authorization") token: String,
        @PageableDefault(size = 100, page = 0, sort = ["createdAt"]) pageable: Pageable,
    ): ResponseEntity<*> {
        val tokenInfo = authClient.validateToken(token).body ?: throw GlobalException(ErrorCode.INVALID_TOKEN_BODY)
        if (!tokenInfo.isValid) {
            throw GlobalException(ErrorCode.INVALID_TOKEN)
        }
        val chatList = chatService.getChatList(tokenInfo.userId!!, pageable)
        val response = ApiResponse(
            message = "success",
            data = chatList,
        )
        return ResponseEntity.ok().body(response)
    }
}