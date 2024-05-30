package kpring.chat.chat

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kpring.chat.chat.model.Chat
import kpring.chat.chat.repository.RoomChatRepository
import kpring.chat.chat.repository.ServerChatRepository
import kpring.chat.chat.service.ChatService
import kpring.chat.chatroom.repository.ChatRoomRepository
import kpring.chat.global.ChatRoomTest
import kpring.chat.global.ChatTest
import kpring.chat.global.CommonTest
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.chat.chat.dto.request.ChatType
import kpring.core.chat.chat.dto.request.CreateChatRequest
import kpring.core.chat.chat.dto.response.ChatResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest

class ChatServiceTest(
  @Value("\${page.size}") val pageSize: Int = 100,
) : FunSpec({
    val roomChatRepository = mockk<RoomChatRepository>()
    val serverChatRepository = mockk<ServerChatRepository>()
    val chatRoomRepository = mockk<ChatRoomRepository>()
    val chatService = ChatService(roomChatRepository, serverChatRepository, chatRoomRepository)

    test("createChat 은 새 Chat을 저장해야 한다") {
      // Given
      val request = CreateChatRequest(id = ChatRoomTest.TEST_ROOM_ID, content = ChatTest.CONTENT, type = ChatType.Room)
      val userId = CommonTest.TEST_USER_ID
      val chat = Chat(userId, request.id, request.content)
      every { roomChatRepository.save(any()) } returns chat

      // When
      chatService.createRoomChat(request, userId)

      // Then
      verify { roomChatRepository.save(any()) }
    }

    test("getChatsByChatRoom 은 권한이 없는 사용자에게 에러 발생") {
      // Given
      val chatRoomId = ChatRoomTest.TEST_ROOM_ID
      val userId = CommonTest.TEST_ANOTHER_USER_ID
      every { chatRoomRepository.existsByIdAndMembersContaining(chatRoomId, userId) } returns false

      // When & Then
      val exception =
        shouldThrow<GlobalException> {
          chatService.getChatsByChatRoom(chatRoomId, userId, 1)
        }
      val errorCodeField = GlobalException::class.java.getDeclaredField("errorCode")
      errorCodeField.isAccessible = true
      val errorCode = errorCodeField.get(exception) as ErrorCode
      errorCode shouldBe ErrorCode.UNAUTHORIZED_CHATROOM
    }

    test("getChatsByChatRoom 은 권한이 있는 사용자에게 Chat들을 반환") {
      // Given
      val chatRoomId = ChatRoomTest.TEST_ROOM_ID
      val userId = CommonTest.TEST_USER_ID
      val chat1 = Chat(userId, chatRoomId, "Message 1")
      val chat2 = Chat(userId, chatRoomId, "Message 2")

      every { roomChatRepository.findAllByRoomId(chatRoomId, pageable = PageRequest.of(1, pageSize)) } returns
        listOf(
          chat1, chat2,
        )
      every { chatRoomRepository.existsByIdAndMembersContaining(chatRoomId, userId) } returns true

      // When
      val chatResponses = chatService.getChatsByChatRoom(chatRoomId, userId, 1)

      // Then
      chatResponses.size shouldBe 2
      chatResponses.shouldContain(ChatResponse(chatRoomId, false, chat1.createdAt, "Message 1"))
      chatResponses.shouldContain(ChatResponse(chatRoomId, false, chat2.createdAt, "Message 2"))
    }
  })
