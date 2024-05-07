package kpring.chat.chatroom

import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kpring.chat.chatroom.model.ChatRoom
import kpring.chat.chatroom.repository.ChatRoomRepository
import kpring.chat.chatroom.service.ChatRoomService
import kpring.chat.global.ChatRoomTest
import kpring.chat.global.CommonTest
import kpring.core.chat.chatroom.dto.request.CreateChatRoomRequest

class ChatRoomServiceTest : FunSpec({

    val chatRoomRepository = mockk<ChatRoomRepository>()
    val chatRoomService = ChatRoomService(chatRoomRepository)

    test("createChatRoom 는 새 ChatRoom을 저장해야 한다") {
        // Given
        val request = CreateChatRoomRequest(ChatRoomTest.TEST_MEMBERS)
        val chatRoom = ChatRoom()
        every { chatRoomRepository.save(any()) } returns chatRoom

        // When
        chatRoomService.createChatRoom(request, CommonTest.TEST_USER_ID)

        // Then
        verify { chatRoomRepository.save(any()) }
    }

})