package kpring.chat.chat

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kpring.chat.chat.model.Chat
import kpring.chat.chat.repository.ChatCustomRepository
import kpring.chat.chat.repository.ChatRepository
import kpring.chat.chat.service.ChatService
import kpring.chat.chatroom.repository.ChatRoomRepository
import kpring.chat.global.ChatTest
import kpring.chat.global.CommonTest
import kpring.chat.global.ContextTest
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.chat.global.util.AccessVerifier
import kpring.core.chat.chat.dto.request.CreateChatRequest
import kpring.core.chat.chat.dto.request.DeleteChatRequest
import kpring.core.chat.chat.dto.request.UpdateChatRequest
import kpring.core.chat.chat.dto.response.EventType
import kpring.core.chat.model.ChatType
import kpring.core.chat.model.MessageType
import kpring.core.server.dto.ServerSimpleInfo
import kpring.core.server.dto.ServerThemeInfo
import org.springframework.beans.factory.annotation.Value
import java.util.*

class ChatServiceTest(
  @Value("\${page.size}") val pageSize: Int = 100,
) : FunSpec({
    val chatRepository = mockk<ChatRepository>()
    val chatRoomRepository = mockk<ChatRoomRepository>()
    val chatCustomRepository = mockk<ChatCustomRepository>()
    val accessVerifier = mockk<AccessVerifier>()
    val chatService = ChatService(chatRepository, chatRoomRepository, chatCustomRepository, accessVerifier)

    test("createChat 은 새 RoomChat을 저장해야 한다") {
      // Given
      val request = CreateChatRequest(content = ChatTest.CONTENT, contextId = ContextTest.TEST_ROOM_ID, type = ChatType.ROOM)
      val userId = CommonTest.TEST_USER_ID
      val chatId = ChatTest.TEST_CHAT_ID
      val roomChat = Chat(chatId, userId, ChatType.ROOM, EventType.CHAT, ContextTest.TEST_ROOM_ID, request.content)
      every { accessVerifier.verifyChatRoomAccess(any(), any()) } just runs
      every { chatRepository.save(any()) } returns roomChat
      every { chatRoomRepository.existsByIdAndMembersContaining(any(), any()) } returns true

      // When
      chatService.createRoomChat(request, userId)

      // Then
      verify { chatRepository.save(any()) }
    }

    // TODO : AccessVerifier의 Test Code로 옮기기
    test("getRoomChats 은 권한이 없는 사용자에게 에러 발생") {
      // Given
      val chatRoomId = ContextTest.TEST_ROOM_ID
      val userId = CommonTest.TEST_ANOTHER_USER_ID
      every { accessVerifier.verifyChatRoomAccess(any(), any()) } throws GlobalException(ErrorCode.FORBIDDEN_CHATROOM)
      every { chatRoomRepository.existsByIdAndMembersContaining(chatRoomId, userId) } returns false

      // When & Then
      val exception =
        shouldThrow<GlobalException> {
          chatService.getRoomChats(chatRoomId, userId, 0, 1)
        }
      val errorCodeField = GlobalException::class.java.getDeclaredField("errorCode")
      errorCodeField.isAccessible = true
      val errorCode = errorCodeField.get(exception) as ErrorCode
      errorCode shouldBe ErrorCode.FORBIDDEN_CHATROOM
    }

    // TODO : AccessVerifier의 Test Code로 옮기기
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
            hostName = "test host name",
            categories = listOf(),
            theme =
              ServerThemeInfo(
                id = "test_theme_id",
                name = "test_theme_name",
              ),
          ),
        )

      // When & Then
      every { accessVerifier.verifyServerAccess(any(), any()) } throws GlobalException(ErrorCode.FORBIDDEN_SERVER)
      val exception =
        shouldThrow<GlobalException> {
          chatService.getServerChats(serverId1, userId, 0, 1, serverList)
        }
      val errorCodeField = GlobalException::class.java.getDeclaredField("errorCode")
      errorCodeField.isAccessible = true
      val errorCode = errorCodeField.get(exception) as ErrorCode
      errorCode shouldBe ErrorCode.FORBIDDEN_SERVER
    }

    test("updateRoomChat 은 권한이 없는 사용자에게 에러 발생") {
      // Given
      val roomId = ContextTest.TEST_ROOM_ID
      val chatId = "test_chat_id"
      val contentUpdate = "content update"
      val request = UpdateChatRequest(id = chatId, contextId = roomId, type = ChatType.ROOM, content = contentUpdate)
      val anotherUserId = CommonTest.TEST_ANOTHER_USER_ID
      val userId = CommonTest.TEST_USER_ID
      val chat =
        Chat(
          chatId,
          userId,
          ChatType.ROOM,
          EventType.CHAT,
          roomId,
          "content",
        )

      every { chatRepository.findById(request.id) } returns Optional.of(chat)

      // When & Then
      val exception =
        shouldThrow<GlobalException> {
          chatService.updateChat(request, anotherUserId)
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
      val request = UpdateChatRequest(id = chatId, contextId = serverId, type = ChatType.SERVER, content = contentUpdate)
      val anotherUserId = CommonTest.TEST_ANOTHER_USER_ID
      val userId = CommonTest.TEST_USER_ID
      val chat =
        Chat(
          chatId,
          userId,
          ChatType.SERVER,
          EventType.CHAT,
          serverId,
          "content",
        )

      every { chatRepository.findById(request.id) } returns Optional.of(chat)

      // When & Then
      val exception =
        shouldThrow<GlobalException> {
          chatService.updateChat(request, anotherUserId)
        }
      val errorCodeField = GlobalException::class.java.getDeclaredField("errorCode")
      errorCodeField.isAccessible = true
      val errorCode = errorCodeField.get(exception) as ErrorCode
      errorCode shouldBe ErrorCode.FORBIDDEN_CHAT
    }

    test("updateRoomChat 은 권한이 있는 사용자의 요청에 따라 Chat 수정") {
      // Given
      val roomId = ContextTest.TEST_ROOM_ID
      val chatId = ChatTest.TEST_CHAT_ID
      val updatedContent = "content update"
      val request =
        UpdateChatRequest(
          id = chatId,
          contextId = roomId,
          type = ChatType.ROOM,
          content = updatedContent,
        )
      val userId = CommonTest.TEST_USER_ID
      val chat =
        Chat(
          chatId,
          userId,
          ChatType.ROOM,
          EventType.CHAT,
          roomId,
          "content",
        )

      every { chatRepository.findById(request.id) } returns Optional.of(chat)

      // When
      val result = chatService.updateChat(request, userId)

      // Then
      result.id shouldBe request.id
      result.content shouldBe request.content
      result.messageType shouldBe MessageType.UPDATE

      verify { chatRepository.save(any()) }
    }

    test("updateServerChat 은 권한이 있는 사용자의 요청에 따라 Chat 수정") {
      // Given
      val serverId = "test_server_id"
      val chatId = "test_chat_id"
      val updatedContent = "content update"
      val request =
        UpdateChatRequest(
          id = chatId,
          contextId = serverId,
          type = ChatType.SERVER,
          content = updatedContent,
        )
      val userId = CommonTest.TEST_USER_ID
      val chat =
        Chat(
          chatId,
          userId,
          ChatType.SERVER,
          EventType.CHAT,
          serverId,
          "content",
        )

      every { chatRepository.findById(request.id) } returns Optional.of(chat)

      // When
      val result = chatService.updateChat(request, userId)

      // Then
      result.id shouldBe request.id
      result.content shouldBe request.content
      result.messageType shouldBe MessageType.UPDATE

      verify { chatRepository.save(any()) }
    }

    test("deleteServerChat 은 권한이 있는 사용자의 요청에 따라 Chat 삭제") {
      // Given
      val serverId = "test_server_id"
      val chatId = "test_chat_id"
      val userId = CommonTest.TEST_USER_ID

      val request =
        DeleteChatRequest(
          id = chatId,
          contextId = serverId,
          type = ChatType.SERVER,
        )

      val chat =
        Chat(
          id = chatId,
          userId = userId,
          eventType = EventType.CHAT,
          chatType = ChatType.SERVER,
          contextId = serverId,
          content = "content",
        )

      every { chatRepository.findById(chatId) } returns Optional.of(chat)
      every { chatRepository.delete(chat) } just Runs

      // When
      val result = chatService.deleteChat(chatId, userId)

      // Then
      result.id shouldBe request.id
      result.messageType shouldBe MessageType.DELETE
    }

    test("deleteRoomChat 은 권한이 있는 사용자의 요청에 따라 Chat 삭제") {
      // Given
      val roomId = "test_room_id"
      val chatId = "test_chat_id"
      val userId = CommonTest.TEST_USER_ID

      val request =
        DeleteChatRequest(
          id = chatId,
          contextId = chatId,
          type = ChatType.SERVER,
        )

      val chat =
        Chat(
          id = chatId,
          userId = userId,
          chatType = ChatType.ROOM,
          eventType = EventType.CHAT,
          contextId = roomId,
          content = "content",
        )

      every { chatRepository.findById(chatId) } returns Optional.of(chat)
      every { chatRepository.delete(chat) } just Runs

      // When
      val result = chatService.deleteChat(chatId, userId)

      // Then
      result.id shouldBe request.id
      result.messageType shouldBe MessageType.DELETE
    }
  })
