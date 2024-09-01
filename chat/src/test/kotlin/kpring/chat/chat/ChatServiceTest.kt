package kpring.chat.chat

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
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
import kpring.core.chat.chat.dto.request.CreateChatRequest
import kpring.core.chat.chat.dto.request.UpdateChatRequest
import kpring.core.chat.model.ChatType
import kpring.core.server.dto.ServerSimpleInfo
import org.springframework.beans.factory.annotation.Value
import java.util.*

class ChatServiceTest(
  @Value("\${page.size}") val pageSize: Int = 100,
) : FunSpec({
    val roomChatRepository = mockk<RoomChatRepository>()
    val serverChatRepository = mockk<ServerChatRepository>()
    val chatRoomRepository = mockk<ChatRoomRepository>()
    val chatService = ChatService(roomChatRepository, serverChatRepository, chatRoomRepository)

    test("createChat 은 새 RoomChat을 저장해야 한다") {
      // Given
      val request = CreateChatRequest(id = ChatRoomTest.TEST_ROOM_ID, content = ChatTest.CONTENT, type = ChatType.Room)
      val userId = CommonTest.TEST_USER_ID
      val chatId = ChatTest.TEST_CHAT_ID
      val roomChat = Chat(chatId, userId, request.id, request.content)
      every { roomChatRepository.save(any()) } returns roomChat

      // When
      chatService.createRoomChat(request, userId)

      // Then
      verify { roomChatRepository.save(any()) }
    }

    test("getRoomChats 은 권한이 없는 사용자에게 에러 발생") {
      // Given
      val chatRoomId = ChatRoomTest.TEST_ROOM_ID
      val userId = CommonTest.TEST_ANOTHER_USER_ID
      every { chatRoomRepository.existsByIdAndMembersContaining(chatRoomId, userId) } returns false

      // When & Then
      val exception =
        shouldThrow<GlobalException> {
          chatService.getRoomChats(chatRoomId, userId, 1)
        }
      val errorCodeField = GlobalException::class.java.getDeclaredField("errorCode")
      errorCodeField.isAccessible = true
      val errorCode = errorCodeField.get(exception) as ErrorCode
      errorCode shouldBe ErrorCode.FORBIDDEN_CHATROOM
    }

    test("getServerChats 은 권한이 없는 사용자에게 에러 발생") {
      // Given
      val serverId1 = "test_server_id"
      val serverId2 = "test_another_server_id"
      val userId = CommonTest.TEST_ANOTHER_USER_ID

      val serverList =
        listOf(
          ServerSimpleInfo(
            id = serverId2,
            name = "test_server_name",
            bookmarked = true,
          ),
        )

      // When & Then
      val exception =
        shouldThrow<GlobalException> {
          chatService.getServerChats(serverId1, userId, 1, serverList)
        }
      val errorCodeField = GlobalException::class.java.getDeclaredField("errorCode")
      errorCodeField.isAccessible = true
      val errorCode = errorCodeField.get(exception) as ErrorCode
      errorCode shouldBe ErrorCode.FORBIDDEN_SERVER
    }

    test("updateRoomChat 은 권한이 없는 사용자에게 에러 발생") {
      // Given
      val roomId = "test_room_id"
      val chatId = "test_chat_id"
      val contentUpdate = "content update"
      val request = UpdateChatRequest(id = chatId, type = ChatType.Room, content = contentUpdate)
      val anotherUserId = CommonTest.TEST_ANOTHER_USER_ID
      val userId = CommonTest.TEST_USER_ID
      val chat =
        Chat(
          chatId,
          userId,
          roomId,
          "content",
        )

      every { roomChatRepository.findById(request.id) } returns Optional.of(chat)

      // When & Then
      val exception =
        shouldThrow<GlobalException> {
          chatService.updateRoomChat(request, anotherUserId)
        }
      val errorCodeField = GlobalException::class.java.getDeclaredField("errorCode")
      errorCodeField.isAccessible = true
      val errorCode = errorCodeField.get(exception) as ErrorCode
      errorCode shouldBe ErrorCode.FORBIDDEN_CHAT
    }

    test("updateServerChat 은 권한이 없는 사용자에게 에러 발생") {
      // Given
      val serverId = "test_server_id"
      val chatId = "test_chat_id"
      val contentUpdate = "content update"
      val request = UpdateChatRequest(id = chatId, type = ChatType.Server, content = contentUpdate)
      val anotherUserId = CommonTest.TEST_ANOTHER_USER_ID
      val userId = CommonTest.TEST_USER_ID
      val chat =
        Chat(
          chatId,
          userId,
          serverId,
          "content",
        )

      every { serverChatRepository.findById(request.id) } returns Optional.of(chat)

      // When & Then
      val exception =
        shouldThrow<GlobalException> {
          chatService.updateServerChat(request, anotherUserId)
        }
      val errorCodeField = GlobalException::class.java.getDeclaredField("errorCode")
      errorCodeField.isAccessible = true
      val errorCode = errorCodeField.get(exception) as ErrorCode
      errorCode shouldBe ErrorCode.FORBIDDEN_CHAT
    }

    test("updateRoomChat 은 권한이 있는 사용자의 요청에 따라 Chat 수정") {
      // Given
      val roomId = "test_room_id"
      val chatId = "test_chat_id"
      val contentUpdate = "content update"
      val request = UpdateChatRequest(id = chatId, type = ChatType.Room, content = contentUpdate)
      val userId = CommonTest.TEST_USER_ID
      val chat =
        Chat(
          chatId,
          userId,
          roomId,
          "content",
        )

      every { roomChatRepository.findById(request.id) } returns Optional.of(chat)

      // When
      val result = chatService.updateRoomChat(request, userId)

      // Then
      result shouldBe true
      verify { roomChatRepository.save(any()) }
    }

    test("updateServerChat 은 권한이 있는 사용자의 요청에 따라 Chat 수정") {
      // Given
      val serverId = "test_server_id"
      val chatId = "test_chat_id"
      val contentUpdate = "content update"
      val request = UpdateChatRequest(id = chatId, type = ChatType.Server, content = contentUpdate)
      val userId = CommonTest.TEST_USER_ID
      val chat =
        Chat(
          chatId,
          userId,
          serverId,
          "content",
        )
      val updated =

        every { serverChatRepository.findById(request.id) } returns Optional.of(chat)

      // When
      val result = chatService.updateRoomChat(request, userId)

      // Then
      result shouldBe true
      verify { roomChatRepository.save(any()) }
    }

    test("deleteServerChat 은 권한이 있는 사용자의 요청에 따라 Chat 삭제") {
      // Given
      val serverId = "test_server_id"
      val chatId = "test_chat_id"
      val userId = CommonTest.TEST_USER_ID
      val chat =
        Chat(
          id = chatId,
          userId = userId,
          contextId = serverId,
          content = "content",
        )

      every { serverChatRepository.findById(chatId) } returns Optional.of(chat)
      every { serverChatRepository.delete(chat) } just Runs

      // When
      val result = chatService.deleteServerChat(chatId, userId)

      // Then
      result shouldBe true
    }

    test("deleteRoomChat 은 권한이 있는 사용자의 요청에 따라 Chat 삭제") {
      // Given
      val roomId = "test_room_id"
      val chatId = "test_chat_id"
      val userId = CommonTest.TEST_USER_ID
      val chat =
        Chat(
          id = chatId,
          userId = userId,
          contextId = roomId,
          content = "content",
        )

      every { roomChatRepository.findById(chatId) } returns Optional.of(chat)
      every { roomChatRepository.delete(chat) } just Runs

      // When
      val result = chatService.deleteRoomChat(chatId, userId)

      // Then
      result shouldBe true
    }
  })
