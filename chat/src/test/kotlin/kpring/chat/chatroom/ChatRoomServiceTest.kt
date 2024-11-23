package kpring.chat.chatroom

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kpring.chat.chat.model.Chat
import kpring.chat.chat.repository.ChatRepository
import kpring.chat.chatroom.dto.InvitationInfo
import kpring.chat.chatroom.model.ChatRoom
import kpring.chat.chatroom.repository.ChatRoomRepository
import kpring.chat.chatroom.service.ChatRoomService
import kpring.chat.chatroom.service.InvitationService
import kpring.chat.global.CommonTest
import kpring.chat.global.ContextTest
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.chat.global.util.AccessVerifier
import kpring.core.chat.chat.dto.response.EventType
import kpring.core.chat.chatroom.dto.request.CreateChatRoomRequest
import kpring.core.chat.chatroom.dto.request.ExpelChatRoomRequest
import kpring.core.chat.chatroom.dto.request.TransferChatRoomOwnerRequest
import kpring.core.chat.model.ChatType
import kpring.core.chat.model.MessageType
import java.util.*

class ChatRoomServiceTest : FunSpec({
  val chatRoomRepository = mockk<ChatRoomRepository>()
  val chatRepository = mockk<ChatRepository>()
  val invitationService = mockk<InvitationService>()
  val accessVerifier = mockk<AccessVerifier>()

  val chatRoomService = ChatRoomService(chatRoomRepository, chatRepository, invitationService, accessVerifier)

  beforeTest {
    clearAllMocks()
  }

  context("createChatRoom 테스트") {
    test("채팅방 생성이 성공적으로 이루어져야 한다") {
      // Given
      val request = CreateChatRoomRequest(ContextTest.TEST_MEMBERS)
      val chatRoom =
        ChatRoom(
          id = "test_room_id",
          ownerId = CommonTest.TEST_USER_ID,
          members = mutableSetOf(CommonTest.TEST_USER_ID),
        ).apply {
          addUsers(ContextTest.TEST_MEMBERS)
        }

      val expectedChat =
        Chat(
          id = "chat_id",
          userId = "",
          chatType = ChatType.ROOM,
          eventType = EventType.SYSTEM,
          contextId = chatRoom.id!!,
          content = "방이 생성되었습니다.",
        )

      every { chatRoomRepository.save(any()) } returns chatRoom
      every { chatRepository.save(any()) } returns expectedChat

      // When
      val result = chatRoomService.createChatRoom(request, CommonTest.TEST_USER_ID)

      // Then
      verify(exactly = 1) { chatRoomRepository.save(any()) }
      verify(exactly = 1) { chatRepository.save(any()) }
      result.chatRoomId shouldBe chatRoom.id
      result.chatResponse.content shouldBe "방이 생성되었습니다."
      result.chatResponse.messageType shouldBe MessageType.CHAT
    }
  }

  context("exitChatRoom 테스트") {
    test("채팅방 나가기가 성공적으로 이루어져야 한다") {
      // Given
      val chatRoomId = ContextTest.TEST_ROOM_ID
      val userId = CommonTest.TEST_USER_ID
      val chatRoom =
        ChatRoom(
          id = chatRoomId,
          ownerId = userId,
          members = mutableSetOf(userId),
        )

      val expectedChat =
        Chat(
          id = "chat_id",
          userId = "",
          chatType = ChatType.ROOM,
          eventType = EventType.SYSTEM,
          contextId = chatRoomId,
          content = "${userId}님이 방에서 나갔습니다.",
        )

      every { accessVerifier.verifyChatRoomAccess(chatRoomId, userId) } just runs
      every { chatRoomRepository.findById(chatRoomId) } returns Optional.of(chatRoom)
      every { chatRoomRepository.save(any()) } returns chatRoom
      every { chatRepository.save(any()) } returns expectedChat

      // When
      val result = chatRoomService.exitChatRoom(chatRoomId, userId)

      // Then
      verify(exactly = 1) { chatRoomRepository.save(any()) }
      verify(exactly = 1) { chatRepository.save(any()) }
      result.chatRoomId shouldBe chatRoomId
      result.chatResponse.content shouldBe "${userId}님이 방에서 나갔습니다."
    }

    test("존재하지 않는 채팅방에 대한 나가기 요청 시 예외가 발생해야 한다") {
      // Given
      val chatRoomId = "non_existent_room_id"
      val userId = CommonTest.TEST_USER_ID

      every { accessVerifier.verifyChatRoomAccess(chatRoomId, userId) } just runs
      every { chatRoomRepository.findById(chatRoomId) } returns Optional.empty()

      // When & Then
      shouldThrow<GlobalException> {
        chatRoomService.exitChatRoom(chatRoomId, userId)
      }.getErrorCode() shouldBe ErrorCode.CHATROOM_NOT_FOUND
    }
  }

  context("getChatRoomInvitation 테스트") {
    test("초대 코드가 없는 경우 새로운 코드를 생성해야 한다") {
      // Given
      val chatRoomId = ContextTest.TEST_ROOM_ID
      val userId = CommonTest.TEST_USER_ID
      val invitationCode = "new_invitation_code"
      val encodedCode = "encoded_invitation_code"

      every { accessVerifier.verifyChatRoomAccess(chatRoomId, userId) } just runs
      every { invitationService.getInvitation(userId, chatRoomId) } returns null
      every { invitationService.setInvitation(userId, chatRoomId) } returns invitationCode
      every { invitationService.generateKeyAndCode(userId, chatRoomId, invitationCode) } returns encodedCode

      // When
      val result = chatRoomService.getChatRoomInvitation(chatRoomId, userId)

      // Then
      verify { invitationService.setInvitation(userId, chatRoomId) }
      result.code shouldBe encodedCode
    }

    test("이미 초대 코드가 있는 경우 기존 코드를 반환해야 한다") {
      // Given
      val chatRoomId = ContextTest.TEST_ROOM_ID
      val userId = CommonTest.TEST_USER_ID
      val existingCode = "existing_code"
      val encodedCode = "encoded_existing_code"

      every { accessVerifier.verifyChatRoomAccess(chatRoomId, userId) } just runs
      every { invitationService.getInvitation(userId, chatRoomId) } returns existingCode
      every { invitationService.generateKeyAndCode(userId, chatRoomId, existingCode) } returns encodedCode

      // When
      val result = chatRoomService.getChatRoomInvitation(chatRoomId, userId)

      // Then
      verify(exactly = 0) { invitationService.setInvitation(any(), any()) }
      result.code shouldBe encodedCode
    }
  }

  context("joinChatRoom 테스트") {
    test("유효한 초대 코드로 채팅방 참여가 성공해야 한다") {
      // Given
      val code = "valid_invitation_code"
      val userId = CommonTest.TEST_USER_ID
      val chatRoomId = ContextTest.TEST_ROOM_ID
      val invitationInfo = InvitationInfo("inviter_id", chatRoomId, "invitation_code")
      val chatRoom = ChatRoom(id = chatRoomId, ownerId = "owner_id", members = mutableSetOf("owner_id"))

      val expectedChat =
        Chat(
          id = "chat_id",
          userId = "",
          chatType = ChatType.ROOM,
          eventType = EventType.SYSTEM,
          contextId = chatRoomId,
          content = "${userId}님이 방에 들어왔습니다.",
        )

      every { invitationService.getInvitationInfoFromCode(code) } returns invitationInfo
      every { invitationService.getInvitation(invitationInfo.userId, invitationInfo.chatRoomId) } returns invitationInfo.code
      every { chatRoomRepository.findById(chatRoomId) } returns Optional.of(chatRoom)
      every { chatRoomRepository.save(any()) } returns chatRoom
      every { chatRepository.save(any()) } returns expectedChat

      // When
      val result = chatRoomService.joinChatRoom(code, userId)

      // Then
      verify { chatRoomRepository.save(any()) }
      result.chatRoomId shouldBe chatRoomId
      result.chatResponse.content shouldBe "${userId}님이 방에 들어왔습니다."
    }

    test("만료된 초대 코드로 채팅방 참여 시 예외가 발생해야 한다") {
      // Given
      val code = "expired_invitation_code"
      val userId = CommonTest.TEST_USER_ID
      val invitationInfo = InvitationInfo("inviter_id", "room_id", "old_code")

      every { invitationService.getInvitationInfoFromCode(code) } returns invitationInfo
      every { invitationService.getInvitation(invitationInfo.userId, invitationInfo.chatRoomId) } returns "different_code"

      // When & Then
      shouldThrow<GlobalException> {
        chatRoomService.joinChatRoom(code, userId)
      }.getErrorCode() shouldBe ErrorCode.EXPIRED_INVITATION
    }
  }

  context("expelFromChatRoom 테스트") {
    test("방장이 유저를 추방할 수 있어야 한다") {
      // Given
      val chatRoomId = ContextTest.TEST_ROOM_ID
      val ownerId = CommonTest.TEST_USER_ID
      val expelUserId = "user_to_expel"
      val request = ExpelChatRoomRequest(chatRoomId, expelUserId)

      val chatRoom =
        ChatRoom(
          id = chatRoomId,
          ownerId = ownerId,
          members = mutableSetOf(ownerId, expelUserId),
        )

      val expectedChat =
        Chat(
          id = "chat_id",
          userId = "",
          chatType = ChatType.ROOM,
          eventType = EventType.SYSTEM,
          contextId = chatRoomId,
          content = "${ownerId}님이 방에서 내보내졌습니다.",
        )

      every { accessVerifier.verifyChatRoomOwner(any(), any()) } just runs
      every { chatRoomRepository.findById(any()) } returns Optional.of(chatRoom)
      every { chatRoomRepository.save(any()) } returns chatRoom
      every { chatRepository.save(any()) } returns expectedChat

      // When
      val result = chatRoomService.expelFromChatRoom(request, ownerId)

      // Then
      verify { chatRoomRepository.save(any()) }
      result.chatRoomId shouldBe chatRoomId
      result.chatResponse.content shouldBe "${ownerId}님이 방에서 내보내졌습니다."
    }

    test("방장이 아닌 사용자가 추방을 시도하면 예외가 발생해야 한다") {
      // Given
      val chatRoomId = ContextTest.TEST_ROOM_ID
      val ownerId = "ownerId"
      val userId = "non_owner_user"
      val expelUserId = "user_to_expel"
      val chatRoom =
        ChatRoom(
          id = chatRoomId,
          ownerId = ownerId,
          members = mutableSetOf(ownerId, expelUserId),
        )
      val request = ExpelChatRoomRequest(chatRoomId, expelUserId)

      every { chatRoomRepository.findById(any()) } returns Optional.of(chatRoom)
      every {
        accessVerifier.verifyChatRoomOwner(
          expelUserId,
          userId,
        )
      } throws GlobalException(ErrorCode.FORBIDDEN_CHATROOM)

      // When & Then
      shouldThrow<GlobalException>({
        chatRoomService.expelFromChatRoom(request, userId)
      })
    }

    test("채팅방 소유권을 성공적으로 위임할 수 있어야 한다") {
      // Given
      val chatRoomId = "test_chat_room_id"
      val currentOwnerId = "current_owner_id"
      val newOwnerId = "new_owner_id"

      val chatRoom =
        ChatRoom(
          id = chatRoomId,
          ownerId = currentOwnerId,
          members = mutableSetOf(currentOwnerId, newOwnerId),
        )

      val expectedChat =
        Chat(
          id = "chat_id",
          userId = "",
          chatType = ChatType.ROOM,
          eventType = EventType.SYSTEM,
          contextId = chatRoomId,
          content = "${newOwnerId}님이 새 방장으로 임명되었습니다.",
        )

      val request = TransferChatRoomOwnerRequest(chatRoomId, newOwnerId)

      every { chatRoomRepository.findById(any()) } returns Optional.of(chatRoom)
      every { accessVerifier.verifyChatRoomOwner(any(), any()) } just runs
      every { chatRoomRepository.save(any()) } returns chatRoom
      every { chatRepository.save(any()) } returns expectedChat

      // When
      val result = chatRoomService.transferChatRoomOwnerShip(request, currentOwnerId)

      // Then
      verify { accessVerifier.verifyChatRoomOwner(any(), any()) }
      verify { chatRoomRepository.save(any()) }
      result.chatResponse.content shouldBe "${newOwnerId}님이 새 방장으로 임명되었습니다."
    }
  }
})
