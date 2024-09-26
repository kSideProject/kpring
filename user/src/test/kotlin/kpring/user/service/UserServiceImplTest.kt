package kpring.user.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kpring.core.global.exception.ServiceException
import kpring.user.dto.request.CreateUserRequest
import kpring.user.entity.User
import kpring.user.exception.UserErrorCode
import kpring.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder

class UserServiceImplTest : FunSpec({

  val userRepository: UserRepository = mockk()
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
  lateinit var createUserRequest: CreateUserRequest

  beforeTest {
    createUserRequest =
      CreateUserRequest(
        TEST_EMAIL,
        TEST_PASSWORD,
        TEST_USERNAME,
      )
    clearMocks(userRepository, passwordEncoder, userValidationService, answers = true)
  }

  test("회원가입_성공") {
    // given
    val userId = 1L
    val user =
      User(
        userId,
        createUserRequest.username,
        createUserRequest.email,
        createUserRequest.password,
        null,
      )

    every { userService.handleDuplicateEmail(createUserRequest.email) } just Runs

    every { userRepository.existsByEmail(createUserRequest.email) } returns false
    every { passwordEncoder.encode(createUserRequest.password) } returns ENCODED_PASSWORD
    every { userRepository.save(any()) } returns user

    // when
    val response = userService.createUser(createUserRequest)

    // then
    verify { userRepository.save(any()) }

    userId shouldBe response.id
    createUserRequest.email shouldBe response.email

    verify { userService.handleDuplicateEmail(createUserRequest.email) }
  }

  test("회원가입_실패_이메일중복케이스") {
    // given
    every { userService.handleDuplicateEmail(TEST_EMAIL) } throws
      ServiceException(UserErrorCode.ALREADY_EXISTS_EMAIL)

    // when
    val exception =
      shouldThrow<ServiceException> {
        userService.handleDuplicateEmail(createUserRequest.email)
      }

    // then
    exception.errorCode.message() shouldBe "이미 존재하는 이메일입니다."

    verify { userRepository.save(any()) wasNot Called }
  }
}) {
  companion object {
    private const val TEST_EMAIL = "test@email.com"
    private const val TEST_PASSWORD = "Password123!"
    private const val ENCODED_PASSWORD = "EncodedPassword123!"
    private const val TEST_USERNAME = "test"
  }
}
