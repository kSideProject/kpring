package kpring.chat.chatroom

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldNotContain
import io.mockk.*
import kpring.chat.chat.model.Chat
import kpring.chat.chat.repository.ChatRepository
import kpring.chat.chatroom.model.ChatRoom
import kpring.chat.chatroom.model.EventType
import kpring.chat.chatroom.repository.ChatRoomRepository
import kpring.chat.chatroom.service.ChatRoomService
import kpring.chat.chatroom.service.InvitationService
import kpring.chat.global.CommonTest
import kpring.chat.global.ContextTest
import kpring.chat.global.util.AccessVerifier
import kpring.core.chat.chatroom.dto.request.CreateChatRoomRequest
import kpring.core.chat.model.ChatType
import java.util.*

class ChatRoomServiceTest : FunSpec({

  val chatRoomRepository = mockk<ChatRoomRepository>()
  val chatRepository = mockk<ChatRepository>()
  val invitationService = mockk<InvitationService>()
  val accessVerifier = mockk<AccessVerifier>()

  val chatRoomService = ChatRoomService(chatRoomRepository, chatRepository, invitationService, accessVerifier)

  test("createChatRoom 는 새 ChatRoom을 저장해야 한다") {
    // Given
    val request = CreateChatRoomRequest(ContextTest.TEST_MEMBERS)
    val chatRoom = ChatRoom("test_chatroom_id", mutableSetOf(CommonTest.TEST_USER_ID))
    val chat = Chat("chat_id", CommonTest.TEST_USER_ID, ChatType.ROOM, EventType.CHAT, ContextTest.TEST_ROOM_ID, "content")
    every { chatRepository.save(any()) } returns chat
    every { chatRoomRepository.save(any()) } returns chatRoom

    // When
    chatRoomService.createChatRoom(request, CommonTest.TEST_USER_ID)

    // Then
    verify { chatRoomRepository.save(any()) }
  }

  test("exitChatRoom 은 요청한 사람이 members의 일원이라면 삭제해야 한다") {
    // Given
    val chatRoom =
      ChatRoom(
        id =
          ContextTest.TEST_ROOM_ID,
      ).apply {
        addUsers(ContextTest.TEST_MEMBERS)
      }
    every { chatRoomRepository.findById(chatRoom.id!!) } returns Optional.of(chatRoom)
    every { chatRoomRepository.save(any()) } returns chatRoom
    every { chatRoomRepository.existsByIdAndMembersContaining(any(), CommonTest.TEST_USER_ID) } returns true
    every { accessVerifier.verifyChatRoomAccess(any(), any()) } just runs
    val chat = Chat("chat_id", CommonTest.TEST_USER_ID, ChatType.ROOM, EventType.CHAT, ContextTest.TEST_ROOM_ID, "content")
    every { chatRepository.save(any()) } returns chat

    // When
    chatRoomService.exitChatRoom(chatRoom.id!!, CommonTest.TEST_USER_ID)

    // Then
    verify { chatRoomRepository.save(chatRoom) }
    chatRoom.members.shouldNotContain(CommonTest.TEST_USER_ID)
  }
})
