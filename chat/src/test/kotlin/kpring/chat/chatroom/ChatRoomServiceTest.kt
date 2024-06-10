package kpring.chat.chatroom

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldNotContain
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kpring.chat.chatroom.model.ChatRoom
import kpring.chat.chatroom.repository.ChatRoomRepository
import kpring.chat.chatroom.service.ChatRoomService
import kpring.chat.chatroom.service.InvitationService
import kpring.chat.global.ChatRoomTest
import kpring.chat.global.CommonTest
import kpring.core.chat.chatroom.dto.request.CreateChatRoomRequest
import java.util.*

class ChatRoomServiceTest : FunSpec({

  val chatRoomRepository = mockk<ChatRoomRepository>()
  val invitationService = mockk<InvitationService>()
  val chatRoomService = ChatRoomService(chatRoomRepository, invitationService)

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

  test("exitChatRoom 은 요청한 사람이 members의 일원이라면 삭제해야 한다") {
    // Given
    val chatRoom =
      ChatRoom().apply {
        id =
          ChatRoomTest.TEST_ROOM_ID
        addUsers(ChatRoomTest.TEST_MEMBERS)
      }
    every { chatRoomRepository.findById(chatRoom.id!!) } returns Optional.of(chatRoom)
    every { chatRoomRepository.save(any()) } returns chatRoom
    every { chatRoomRepository.existsByIdAndMembersContaining(any(), CommonTest.TEST_USER_ID) } returns true

    // When
    chatRoomService.exitChatRoom(chatRoom.id!!, CommonTest.TEST_USER_ID)

    // Then
    verify { chatRoomRepository.save(chatRoom) }
    chatRoom.members.shouldNotContain(CommonTest.TEST_USER_ID)
  }
})
