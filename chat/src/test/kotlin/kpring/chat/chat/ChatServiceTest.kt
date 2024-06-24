package kpring.chat.chat

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kpring.chat.chat.model.RoomChat
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
import kpring.core.server.dto.ServerSimpleInfo
import kpring.core.server.dto.ServerThemeInfo
import org.springframework.beans.factory.annotation.Value

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
      val roomChat = RoomChat(userId, request.id, request.content)
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
      val exception =
        shouldThrow<GlobalException> {
          chatService.getServerChats(serverId1, userId, 1, serverList)
        }
      val errorCodeField = GlobalException::class.java.getDeclaredField("errorCode")
      errorCodeField.isAccessible = true
      val errorCode = errorCodeField.get(exception) as ErrorCode
      errorCode shouldBe ErrorCode.FORBIDDEN_SERVER
    }
  })
