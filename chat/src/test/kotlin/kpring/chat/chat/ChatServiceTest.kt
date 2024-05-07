package kpring.chat.chat

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kpring.chat.chat.model.Chat
import kpring.chat.chat.repository.ChatRepository
import kpring.chat.chat.service.ChatService
import kpring.chat.chatroom.repository.ChatRoomRepository
import kpring.chat.global.ChatRoomTest
import kpring.chat.global.ChatTest
import kpring.chat.global.CommonTest
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.chat.chat.dto.request.CreateChatRequest
import kpring.core.chat.chat.dto.response.ChatResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest

class ChatServiceTest(
    @Value("\${page.size}") val pageSize: Int = 100
) : FunSpec({
    val chatRepository = mockk<ChatRepository>()
    val chatRoomRepository = mockk<ChatRoomRepository>()
    val chatService = ChatService(chatRepository, chatRoomRepository)

    test("createChat 은 새 Chat을 저장해야 한다") {
        // Given
        val request = CreateChatRequest(ChatRoomTest.TEST_ROOM_ID, ChatTest.CONTENT)
        val userId = CommonTest.TEST_USER_ID
        val chat = Chat(userId, request.room, request.content)
        every { chatRepository.save(any()) } returns chat

        // When
        chatService.createChat(request, userId)

        // Then
        verify { chatRepository.save(any()) }
    }


})