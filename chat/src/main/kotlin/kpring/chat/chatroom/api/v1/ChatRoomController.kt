package kpring.chat.chatroom.api.v1

import kpring.chat.chatroom.service.ChatRoomService
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.auth.client.AuthClient
import kpring.core.chat.chatroom.dto.request.CreateChatRoomRequest
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ChatRoomController (
    private val chatRoomService: ChatRoomService,
    private val authClient: AuthClient
){
    @PostMapping("/chatroom")
    fun createChatRoom(
        @Validated @RequestBody request: CreateChatRoomRequest,
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<*> {
        val tokenResponse = authClient.validateToken(token)
        val body = tokenResponse.body?: throw GlobalException(ErrorCode.INVALID_TOKEN_BODY)
        val userId = body.userId?: throw GlobalException(ErrorCode.USERID_NOT_EXIST)
        val isValid = body.isValid
        if(!isValid){
            throw GlobalException(ErrorCode.INVALID_TOKEN)
        }
        val result = chatRoomService.createChatRoom(request, userId)
        return ResponseEntity.ok().body(result)
    }
}