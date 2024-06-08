package kpring.chat.chatroom

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kpring.chat.chatroom.dto.Lock
import kpring.chat.chatroom.model.ChatRoom
import kpring.chat.chatroom.repository.ChatRoomRepository
import kpring.chat.chatroom.service.ChatRoomService
import kpring.chat.chatroom.service.DistributedLockService
import kpring.chat.global.ChatRoomTest
import kpring.chat.global.CommonTest
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.chat.chatroom.dto.request.CreateChatRoomRequest
import java.util.*

class ChatRoomServiceTest : FunSpec({

  val chatRoomRepository = mockk<ChatRoomRepository>()
  val lockService = mockk<DistributedLockService>()
  val chatRoomService = ChatRoomService(chatRoomRepository, lockService)

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
        id = ChatRoomTest.TEST_ROOM_ID
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

  test("inviteToChatRoomByUserIdWithLock 는 동시성 잠금을 획득한 후 사용자를 초대해야 한다") {
    // Given
    val chatRoom =
      ChatRoom().apply {
        id = ChatRoomTest.TEST_ROOM_ID
        addUsers(ChatRoomTest.TEST_MEMBERS)
      }
    val lock = Lock("chatRoom:${chatRoom.id}:lock", UUID.randomUUID().toString(), true)

    every { lockService.getLock(any()) } returns lock
    every { lockService.releaseLock(any(), any()) } returns Unit
    every { chatRoomRepository.findById(chatRoom.id!!) } returns Optional.of(chatRoom)
    every { chatRoomRepository.save(any()) } returns chatRoom
    every { chatRoomRepository.existsByIdAndMembersContaining(any(), CommonTest.TEST_USER_ID) } returns true

    // When
    chatRoomService.inviteToChatRoomByUserIdWithLock(CommonTest.TEST_USER_ID, CommonTest.TEST_USER_ID, chatRoom.id!!)

    // Then
    verify { lockService.getLock(any()) }
    verify { chatRoomRepository.save(chatRoom) }
    verify { lockService.releaseLock(any(), any()) }
    chatRoom.members.shouldContain(CommonTest.TEST_USER_ID)
  }

  test("inviteToChatRoomByUserId 는 사용자를 초대해야 한다") {
    // Given
    val chatRoom =
      ChatRoom().apply {
        id = ChatRoomTest.TEST_ROOM_ID
        addUsers(ChatRoomTest.TEST_MEMBERS)
      }

    every { chatRoomRepository.findById(chatRoom.id!!) } returns Optional.of(chatRoom)
    every { chatRoomRepository.save(any()) } returns chatRoom
    every { chatRoomRepository.existsByIdAndMembersContaining(any(), CommonTest.TEST_USER_ID) } returns true

    // When
    chatRoomService.inviteToChatRoomByUserId(CommonTest.TEST_USER_ID, CommonTest.TEST_USER_ID, chatRoom.id!!)

    // Then
    verify { chatRoomRepository.save(chatRoom) }
    chatRoom.members.shouldContain(CommonTest.TEST_USER_ID)
  }

  test("inviteToChatRoomByUserIdWithLock 는 잠금 획득 실패 시 예외를 발생해야 한다") {
    // Given
    val chatRoomId = ChatRoomTest.TEST_ROOM_ID
    val userId = CommonTest.TEST_USER_ID

    every { lockService.getLock(any()) } throws GlobalException(ErrorCode.CONCURRENCY_CONFLICTION)

    // When
    val exception =
      shouldThrow<GlobalException> {
        chatRoomService.inviteToChatRoomByUserIdWithLock(userId, userId, chatRoomId)
      }

    // Then
    exception.getErrorCode() shouldBe ErrorCode.CONCURRENCY_CONFLICTION
  }
})
