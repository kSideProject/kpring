package kpring.user.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kpring.core.global.exception.ServiceException
import kpring.user.entity.Friend
import kpring.user.entity.FriendRequestStatus
import kpring.user.entity.User
import kpring.user.exception.UserErrorCode
import kpring.user.global.CommonTest
import kpring.user.repository.FriendRepository
import kpring.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import java.nio.file.Paths

internal class FriendServiceImplTest : FunSpec({
  val userRepository: UserRepository = mockk()
  val friendRepository: FriendRepository = mockk()
  val passwordEncoder: PasswordEncoder = mockk()
  val userValidationService: UserValidationService = mockk()
  val uploadProfileImageService: UploadProfileImageService = mockk()
  val userService =
    UserServiceImpl(
      userRepository,
      passwordEncoder,
      userValidationService,
      uploadProfileImageService,
    )
  val friendService =
    FriendServiceImpl(
      userService,
      friendRepository,
    )

  test("친구신청_성공") {
    // given
    val user = mockk<User> { every { id } returns CommonTest.TEST_USER_ID }
    val friend = mockk<User> { every { id } returns CommonTest.TEST_FRIEND_ID }

    every { userService.getUser(CommonTest.TEST_USER_ID) } returns user
    every { userService.getUser(CommonTest.TEST_FRIEND_ID) } returns friend

    every {
      friendRepository.existsByUserIdAndFriendId(CommonTest.TEST_USER_ID, CommonTest.TEST_FRIEND_ID)
    } returns false
    every { user.requestFriend(friend) } just Runs
    every { friend.receiveFriendRequest(user) } just Runs

    // when
    val response = friendService.addFriend(CommonTest.TEST_USER_ID, CommonTest.TEST_FRIEND_ID)

    // then
    response.friendId shouldBe CommonTest.TEST_FRIEND_ID

    verify { friendService.checkSelfFriend(user, friend) }
    verify {
      friendService.checkFriendRelationExists(CommonTest.TEST_USER_ID, CommonTest.TEST_FRIEND_ID)
    }
  }

  test("친구신청_실패_자기자신을 친구로 추가하는 케이스") {
    // given
    val user = mockk<User>()

    every { userService.getUser(CommonTest.TEST_USER_ID) } returns user
    every {
      friendRepository.existsByUserIdAndFriendId(CommonTest.TEST_USER_ID, CommonTest.TEST_USER_ID)
    } returns false
    every {
      friendService.checkSelfFriend(user, user)
    } throws ServiceException(UserErrorCode.NOT_SELF_FOLLOW)

    // when
    val exception =
      shouldThrow<ServiceException> {
        friendService.addFriend(CommonTest.TEST_USER_ID, CommonTest.TEST_USER_ID)
      }

    // then
    exception.errorCode.message() shouldBe "자기자신에게 친구요청을 보낼 수 없습니다"

    verify(exactly = 0) { user.requestFriend(any()) }
    verify(exactly = 0) { user.receiveFriendRequest(any()) }
  }

  test("친구신청_실패_이미 친구인 케이스") {
    // given
    val user = mockk<User>()
    val friend = mockk<User>()

    every { userService.getUser(CommonTest.TEST_USER_ID) } returns user
    every { userService.getUser(CommonTest.TEST_FRIEND_ID) } returns friend

    every {
      friendRepository.existsByUserIdAndFriendId(CommonTest.TEST_USER_ID, CommonTest.TEST_FRIEND_ID)
    } returns true
    every {
      friendService.checkFriendRelationExists(CommonTest.TEST_USER_ID, CommonTest.TEST_FRIEND_ID)
    } throws ServiceException(UserErrorCode.ALREADY_FRIEND)

    // when
    val exception =
      shouldThrow<ServiceException> {
        friendService.addFriend(CommonTest.TEST_USER_ID, CommonTest.TEST_FRIEND_ID)
      }

    // then
    exception.errorCode.message() shouldBe "이미 친구입니다."

    verify { friendService.checkSelfFriend(user, friend) }
    verify {
      friendService.checkFriendRelationExists(CommonTest.TEST_USER_ID, CommonTest.TEST_FRIEND_ID)
    }
  }

  test("친구신청조회_성공") {
    // given
    val friendInfo =
      mockk<User> {
        every { id } returns CommonTest.TEST_FRIEND_ID
        every { username } returns CommonTest.TEST_FRIEND_USERNAME
      }
    val friend =
      mockk<Friend> {
        every { friend } returns friendInfo
      }

    val friendList = listOf(friend)
    every {
      friendRepository.findAllByUserIdAndRequestStatus(
        CommonTest.TEST_USER_ID,
        FriendRequestStatus.RECEIVED,
      )
    } returns friendList

    // when
    val response = friendService.getFriendRequests(CommonTest.TEST_USER_ID)

    // then
    response.userId shouldBe CommonTest.TEST_USER_ID
    for (request in response.friendRequests) {
      request.friendId shouldBe CommonTest.TEST_FRIEND_ID
      request.username shouldBe CommonTest.TEST_FRIEND_USERNAME
    }
  }

  test("친구신청수락_성공") {
    // given
    val receivedFriend =
      mockk<Friend> {
        every { requestStatus } returns FriendRequestStatus.RECEIVED
      }
    val requestedFriend =
      mockk<Friend> {
        every { requestStatus } returns FriendRequestStatus.REQUESTED
      }

    every {
      friendRepository.findByUserIdAndFriendId(
        CommonTest.TEST_USER_ID,
        CommonTest.TEST_FRIEND_ID,
      )
    } returns receivedFriend
    every {
      friendRepository.findByUserIdAndFriendId(
        CommonTest.TEST_FRIEND_ID,
        CommonTest.TEST_USER_ID,
      )
    } returns requestedFriend

    every { receivedFriend.updateRequestStatus(any()) } just Runs
    every { requestedFriend.updateRequestStatus(any()) } just Runs

    // when
    val response =
      friendService.acceptFriendRequest(CommonTest.TEST_USER_ID, CommonTest.TEST_FRIEND_ID)

    // then
    response.friendId shouldBe CommonTest.TEST_FRIEND_ID

    verify(exactly = 2) {
      friendRepository.findByUserIdAndFriendId(any(), any())
    }
  }

  test("친구신청수락_실패_해당하는 친구신청이 없는 케이스") {
    // given
    every {
      friendRepository.findByUserIdAndFriendId(
        CommonTest.TEST_USER_ID,
        CommonTest.TEST_FRIEND_ID,
      )
    } throws ServiceException(UserErrorCode.FRIENDSHIP_NOT_FOUND)

    // when
    val exception =
      shouldThrow<ServiceException> {
        friendService.acceptFriendRequest(CommonTest.TEST_USER_ID, CommonTest.TEST_FRIEND_ID)
      }

    // then
    exception.errorCode.message() shouldBe "해당하는 친구신청이 없습니다."
  }

  test("친구신청수락_실패_이미 친구인 케이스") {
    // given
    every {
      friendRepository.findByUserIdAndFriendId(
        CommonTest.TEST_USER_ID,
        CommonTest.TEST_FRIEND_ID,
      )
    } throws ServiceException(UserErrorCode.FRIENDSHIP_ALREADY_EXISTS)
    every {
      friendRepository.findByUserIdAndFriendId(
        CommonTest.TEST_FRIEND_ID,
        CommonTest.TEST_USER_ID,
      )
    } throws ServiceException(UserErrorCode.FRIENDSHIP_ALREADY_EXISTS)

    // when
    val exception =
      shouldThrow<ServiceException> {
        friendService.acceptFriendRequest(CommonTest.TEST_USER_ID, CommonTest.TEST_FRIEND_ID)
      }

    // then
    exception.errorCode.message() shouldBe "이미 친구입니다."
  }

  test("친구삭제_성공") {
    // given
    val user = mockk<User>()
    val friend = mockk<User>()
    val userFriendRelation = mockk<Friend>()

    every { userService.getUser(CommonTest.TEST_USER_ID) } returns user
    every { userService.getUser(CommonTest.TEST_FRIEND_ID) } returns friend

    every {
      friendRepository.findByUserIdAndFriendIdAndRequestStatus(
        CommonTest.TEST_USER_ID,
        CommonTest.TEST_FRIEND_ID,
        FriendRequestStatus.ACCEPTED,
      )
    } returns userFriendRelation

    every { user.removeFriendRelation(userFriendRelation) } just Runs

    // when
    val response =
      friendService.deleteFriend(CommonTest.TEST_USER_ID, CommonTest.TEST_FRIEND_ID)

    // then
    response.friendId shouldBe CommonTest.TEST_FRIEND_ID
  }

  test("친구삭제_실패_해당하는 친구가 없는 케이스") {
    // given
    val user = mockk<User>()
    val friend = mockk<User>()

    every { userService.getUser(CommonTest.TEST_USER_ID) } returns user
    every { userService.getUser(CommonTest.TEST_FRIEND_ID) } returns friend
    every {
      friendRepository.findByUserIdAndFriendIdAndRequestStatus(
        CommonTest.TEST_USER_ID,
        CommonTest.TEST_FRIEND_ID,
        FriendRequestStatus.ACCEPTED,
      )
    } throws ServiceException(UserErrorCode.FRIEND_NOT_FOUND)

    // when
    val exception =
      shouldThrow<ServiceException> {
        friendService.deleteFriend(CommonTest.TEST_USER_ID, CommonTest.TEST_FRIEND_ID)
      }

    // then
    exception.errorCode.message() shouldBe "해당하는 친구가 없습니다."
  }

  test("친구조회_성공") {
    // given
    val dir = System.getProperty("user.dir")
    val imagePath = Paths.get(dir)
    val friendInfo =
      mockk<User> {
        every { id } returns CommonTest.TEST_FRIEND_ID
        every { username } returns CommonTest.TEST_FRIEND_USERNAME
        every { email } returns CommonTest.TEST_FRIEND_EMAIL
        every { file } returns CommonTest.TEST_PROFILE_IMG
      }
    val friend =
      mockk<Friend> {
        every { friend } returns friendInfo
      }

    val friends = listOf(friend)
    every {
      friendRepository.findAllByUserIdAndRequestStatus(
        CommonTest.TEST_USER_ID,
        FriendRequestStatus.ACCEPTED,
      )
    } returns friends

    // when
    val response = friendService.getFriends(CommonTest.TEST_USER_ID)

    // then
    response.userId shouldBe CommonTest.TEST_USER_ID
    for (request in response.friends) {
      request.friendId shouldBe CommonTest.TEST_FRIEND_ID
      request.username shouldBe CommonTest.TEST_FRIEND_USERNAME
      request.email shouldBe CommonTest.TEST_FRIEND_EMAIL
      request.imagePath shouldBe imagePath.resolve(CommonTest.TEST_PROFILE_IMG)
    }
  }
})
