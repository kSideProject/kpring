package kpring.user.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kpring.core.global.exception.ServiceException
import kpring.user.entity.User
import kpring.user.exception.UserErrorCode
import kpring.user.global.CommonTest
import kpring.user.repository.FriendRepository
import kpring.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder

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
    val user = mockk<User>(relaxed = true)
    val friend = mockk<User>(relaxed = true)

    every { userService.getUser(CommonTest.TEST_USER_ID) } returns user
    every { userService.getUser(CommonTest.TEST_FRIEND_ID) } returns friend

    every {
      friendRepository.existsByUserIdAndFriendId(CommonTest.TEST_USER_ID, CommonTest.TEST_FRIEND_ID)
    } returns false
    every { user.requestFriend(friend) } just Runs
    every { friend.receiveFriendRequest(user) } just Runs

    val response = friendService.addFriend(CommonTest.TEST_USER_ID, CommonTest.TEST_FRIEND_ID)
    response.friendId shouldBe friend.id

    verify { friendService.checkSelfFriend(user, friend) }
    verify {
      friendService.checkFriendRelationExists(CommonTest.TEST_USER_ID, CommonTest.TEST_FRIEND_ID)
    }
  }

  test("친구신청_실패_자기자신을 친구로 추가하는 케이스") {
    val user = mockk<User>(relaxed = true)

    every { userService.getUser(CommonTest.TEST_USER_ID) } returns user
    every {
      friendRepository.existsByUserIdAndFriendId(CommonTest.TEST_USER_ID, CommonTest.TEST_USER_ID)
    } returns false
    every {
      friendService.checkSelfFriend(user, user)
    } throws ServiceException(UserErrorCode.NOT_SELF_FOLLOW)

    val exception =
      shouldThrow<ServiceException> {
        friendService.addFriend(CommonTest.TEST_USER_ID, CommonTest.TEST_USER_ID)
      }
    exception.errorCode.message() shouldBe "자기자신에게 친구요청을 보낼 수 없습니다"

    verify(exactly = 0) { user.requestFriend(any()) }
    verify(exactly = 0) { user.receiveFriendRequest(any()) }
  }

  test("친구신청_실패_이미 친구인 케이스") {
    val user = mockk<User>(relaxed = true)
    val friend = mockk<User>(relaxed = true)

    every { userService.getUser(CommonTest.TEST_USER_ID) } returns user
    every { userService.getUser(CommonTest.TEST_FRIEND_ID) } returns friend

    every {
      friendRepository.existsByUserIdAndFriendId(CommonTest.TEST_USER_ID, CommonTest.TEST_FRIEND_ID)
    } returns true
    every {
      friendService.checkFriendRelationExists(CommonTest.TEST_USER_ID, CommonTest.TEST_FRIEND_ID)
    } throws ServiceException(UserErrorCode.ALREADY_FRIEND)

    val exception =
      shouldThrow<ServiceException> {
        friendService.addFriend(CommonTest.TEST_USER_ID, CommonTest.TEST_FRIEND_ID)
      }
    exception.errorCode.message() shouldBe "이미 친구입니다."

    verify { friendService.checkSelfFriend(user, friend) }
    verify {
      friendService.checkFriendRelationExists(CommonTest.TEST_USER_ID, CommonTest.TEST_FRIEND_ID)
    }
  }
})
