package kpring.user.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kpring.user.dto.request.CreateUserRequest
import kpring.user.entity.User
import kpring.user.exception.ErrorCode
import kpring.user.exception.ExceptionWrapper
import kpring.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder

class UserServiceImplTest() : FunSpec({
    val userRepository: UserRepository = mockk()
    val passwordEncoder: PasswordEncoder = mockk()
    val userValidationService: UserValidationService = mockk()
    val userService = UserServiceImpl(
        userRepository,
        passwordEncoder,
        userValidationService
    )
    val friendService = FriendService(userRepository)
    lateinit var createUserRequest: CreateUserRequest

    beforeTest {
        createUserRequest = CreateUserRequest(
            TEST_EMAIL,
            TEST_PASSWORD,
            TEST_PASSWORD,
            TEST_USERNAME
        )
        clearMocks(userRepository, passwordEncoder, userValidationService, answers = true)
    }

    test("회원가입_성공") {
        val userId = 1L
        val user = User(
            userId,
            createUserRequest.username,
            createUserRequest.email,
            createUserRequest.password
        )

        every { userValidationService.validateDuplicateEmail(createUserRequest.email) } just Runs
        every {
            userValidationService.validatePasswordMatch(
                createUserRequest.password,
                createUserRequest.passwordCheck
            )
        } just Runs

        every { userRepository.existsByEmail(createUserRequest.email) } returns false
        every { passwordEncoder.encode(createUserRequest.password) } returns ENCODED_PASSWORD
        every { userRepository.save(any()) } returns user

        val response = userService.createUser(createUserRequest)

        verify { userRepository.save(any()) }

        userId shouldBe response.id
        createUserRequest.email shouldBe response.email

        verify { userValidationService.validateDuplicateEmail(createUserRequest.email) }
        verify {
            userValidationService.validatePasswordMatch(
                createUserRequest.password,
                createUserRequest.passwordCheck
            )
        }
    }

    test("회원가입_실패_이메일중복케이스") {
        every { userValidationService.validateDuplicateEmail(TEST_EMAIL) } throws
                ExceptionWrapper(ErrorCode.ALREADY_EXISTS_EMAIL)

        val exception = shouldThrow<ExceptionWrapper> {
            userValidationService.validateDuplicateEmail(createUserRequest.email)
        }
        exception.errorCode.message shouldBe "Email already exists"

        verify { userRepository.save(any()) wasNot Called }
    }

//    test("친구추가_성공") {
//        val user = User(id = 1L, username = "user1", followers = mutableSetOf(), followees = mutableSetOf())
//        val friend = User(id = 2L, username = "user2", followers = mutableSetOf(), followees = mutableSetOf())
//
//        `when`(userRepository.findById(user.id!!)).thenReturn(Optional.of(user))
//        `when`(userRepository.findById(friend.id!!)).thenReturn(Optional.of(friend))
//
//        val result: AddFriendResponse = friendService.addFriend(AddFriendRequest(friend.id!!), user.id)
//
//        assertEquals(friend.id!!, result.friendId)
//
//        // Verify that the followers and followees relationships are updated
//        user.followers.forEach { follower ->
//            assertEquals(1, follower.followees.size)
//            assertEquals(user, follower.followees.first())
//        }
//        user.followees.forEach { followee ->
//            assertEquals(1, followee.followers.size)
//            assertEquals(user, followee.followers.first())
//        }
//
//        verify(userRepository).findById(user.id!!)
//    }
//
//    test("친구추가실패_유저조회불가능케이스") {
//        val userId = 2L
//        val friendId = 1L
//
//        `when`(userRepository.findById(userId)).thenReturn(Optional.empty())
//
//        val exception = assertThrows(IllegalArgumentException::class.java) {
//            friendService.addFriend(AddFriendRequest(friendId), userId)
//        }
//
//        assertEquals("User not found", exception.message)
//
//        verify(userRepository).findById(userId)
//    }
}) {
    companion object {
        private const val TEST_EMAIL = "test@email.com"
        private const val TEST_PASSWORD = "Password123!"
        private const val ENCODED_PASSWORD = "EncodedPassword123!"
        private const val TEST_USERNAME = "test"
        private const val MISMATCHED_PASSWORD = "MismatchedPassword123!"
    }
}