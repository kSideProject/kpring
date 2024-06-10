package kpring.user.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kpring.core.auth.client.AuthClient
import kpring.core.auth.dto.response.TokenInfo
import kpring.core.auth.dto.response.TokenValidationResponse
import kpring.core.auth.enums.TokenType
import kpring.core.global.dto.response.ApiResponse
import kpring.core.global.exception.ServiceException
import kpring.test.restdoc.dsl.restDoc
import kpring.test.restdoc.json.JsonDataType.*
import kpring.user.dto.request.CreateUserRequest
import kpring.user.dto.request.UpdateUserProfileRequest
import kpring.user.dto.response.CreateUserResponse
import kpring.user.dto.response.FailMessageResponse
import kpring.user.dto.response.GetUserProfileResponse
import kpring.user.dto.response.UpdateUserProfileResponse
import kpring.user.exception.UserErrorCode
import kpring.user.service.UserService
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.mock.web.MockMultipartFile
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.reactive.function.BodyInserters

@WebMvcTest(controllers = [UserController::class])
@ExtendWith(value = [MockKExtension::class])
class UserControllerTest(
  private val objectMapper: ObjectMapper,
  webContext: WebApplicationContext,
  @MockkBean val authClient: AuthClient,
  @MockkBean val userService: UserService,
) : DescribeSpec(
    {

      val restDocument = ManualRestDocumentation()
      val webTestClient =
        MockMvcWebTestClient.bindToApplicationContext(webContext)
          .configureClient()
          .filter(
            documentationConfiguration(restDocument)
              .operationPreprocessors()
              .withRequestDefaults(prettyPrint())
              .withResponseDefaults(prettyPrint()),
          )
          .build()

      beforeSpec { restDocument.beforeTest(this.javaClass, "user controller") }

      afterSpec { restDocument.afterTest() }

      afterTest { clearMocks(authClient) }

      describe("회원가입 API") {

        it("회원가입 성공") {
          // given
          val request =
            CreateUserRequest.builder()
              .email(TEST_EMAIL)
              .password(TEST_PASSWORD)
              .username(TEST_USERNAME)
              .build()
          val response = CreateUserResponse.builder().build()
          every { userService.createUser(request) } returns response

          // when
          val result =
            webTestClient.post()
              .uri("/api/v1/user")
              .contentType(MediaType.APPLICATION_JSON)
              .bodyValue(request)
              .exchange()

          // then
          val docsRoot =
            result
              .expectStatus().isOk
              .expectBody()

          // docs
          docsRoot
            .restDoc("createUser200", "회원가입 API") {
              request {
                header {
                  "Content-Type" mean "application/json"
                }
                body {
                  "email" type Strings mean "이메일"
                  "password" type Strings mean "비밀번호"
                  "username" type Strings mean "사용자 이름"
                }
              }
            }
        }

        it("회원가입 실패 : 이미 존재하는 이메일") {
          // given
          val request =
            CreateUserRequest.builder()
              .email(TEST_EMAIL)
              .password(TEST_PASSWORD)
              .username(TEST_USERNAME)
              .build()
          val exception = ServiceException(UserErrorCode.ALREADY_EXISTS_EMAIL)
          val response = FailMessageResponse.builder().message(exception.errorCode.message()).build()
          every { userService.createUser(request) } throws exception

          // when
          val result =
            webTestClient.post()
              .uri("/api/v1/user")
              .contentType(MediaType.APPLICATION_JSON)
              .bodyValue(request)
              .exchange()

          // then
          val docsRoot =
            result
              .expectStatus().isBadRequest
              .expectBody().json(
                objectMapper.writeValueAsString(response),
              )

          // docs
          docsRoot
            .restDoc(
              identifier = "createUser400",
              description = "회원가입 API",
            ) {
              request {
                header {
                  "Content-Type" mean "application/json"
                }
                body {
                  "email" type Strings mean "이메일"
                  "password" type Strings mean "비밀번호"
                  "username" type Strings mean "사용자 이름"
                }
              }

              response {
                body {
                  "message" type Strings mean "에러 메시지"
                }
              }
            }
        }

        it("회원가입 실패 : 필수입력 값 미전송") {
          // given
          val request =
            CreateUserRequest.builder()
              .password(TEST_PASSWORD)
              .username(TEST_USERNAME)
              .build()
          val responseMessage = "이메일이 누락되었습니다."
          val response = FailMessageResponse.builder().message(responseMessage).build()

          // when
          val result =
            webTestClient.post()
              .uri("/api/v1/user")
              .contentType(MediaType.APPLICATION_JSON)
              .bodyValue(request)
              .exchange()

          // then
          val docsRoot =
            result
              .expectStatus().isBadRequest
              .expectBody().json(
                objectMapper.writeValueAsString(response),
              )

          // docs
          docsRoot
            .restDoc(
              identifier = "createUser400",
              description = "회원가입 API",
            ) {
              request {
                header {
                  "Content-Type" mean "application/json"
                }
                body {
                  "email" type Strings mean "이메일"
                  "password" type Strings mean "비밀번호"
                  "username" type Strings mean "사용자 이름"
                }
              }

              response {
                body {
                  "message" type Strings mean "에러 메시지"
                }
              }
            }
        }

        it("회원가입 실패 : 서버 내부 오류") {
          // given
          val request =
            CreateUserRequest.builder()
              .email(TEST_EMAIL)
              .password(TEST_PASSWORD)
              .username(TEST_USERNAME)
              .build()
          val exception = RuntimeException("서버 내부 오류")
          val response = FailMessageResponse.builder().message("서버 오류").build()
          every { userService.createUser(request) } throws exception

          // when
          val result =
            webTestClient.post()
              .uri("/api/v1/user")
              .contentType(MediaType.APPLICATION_JSON)
              .bodyValue(request)
              .exchange()

          // then
          val docsRoot =
            result
              .expectStatus().is5xxServerError
              .expectBody().json(
                objectMapper.writeValueAsString(response),
              )

          // docs
          docsRoot
            .restDoc(
              identifier = "createUser500",
              description = "회원가입 API",
            ) {
              request {
                header {
                  "Content-Type" mean "application/json"
                }
                body {
                  "email" type Strings mean "이메일"
                  "password" type Strings mean "비밀번호"
                  "username" type Strings mean "사용자 이름"
                }
              }

              response {
                body {
                  "message" type Strings mean "에러 메시지"
                }
              }
            }
        }
      }

      describe("회원정보 수정 API") {
        it("회원정보 수정 성공") {
          // given
          val userId = 1L
          val request =
            UpdateUserProfileRequest.builder()
              .email(TEST_EMAIL)
              .username(TEST_USERNAME)
              .password(TEST_PASSWORD)
              .newPassword(TEST_NEW_PASSWORD)
              .build()

          val fileResource = ClassPathResource(TEST_PROFILE_IMG)
          val file =
            MockMultipartFile(
              "image",
              fileResource.filename,
              MediaType.IMAGE_JPEG_VALUE,
              fileResource.inputStream,
            )
          val data =
            UpdateUserProfileResponse.builder()
              .email(TEST_EMAIL)
              .username(TEST_USERNAME)
              .build()

          val requestJson = objectMapper.writeValueAsString(request)

          val response = ApiResponse(data = data)
          every { authClient.getTokenInfo(any()) }.returns(
            ApiResponse(data = TokenInfo(TokenType.ACCESS, userId.toString())),
          )
          every { userService.updateProfile(userId, any(), any()) } returns data

          val bodyBuilder = MultipartBodyBuilder()
          bodyBuilder.part("json", requestJson, MediaType.APPLICATION_JSON)
          fileResource.filename?.let {
            bodyBuilder.part("file", ByteArrayResource(file.bytes), MediaType.IMAGE_JPEG).filename(
              it,
            )
          }

          // when
          val result =
            webTestClient.patch()
              .uri("/api/v1/user/{userId}", userId)
              .header("Authorization", "Bearer token")
              .contentType(MediaType.MULTIPART_FORM_DATA)
              .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
              .exchange()

          // then
          val docsRoot =
            result
              .expectStatus().isOk
              .expectBody().json(objectMapper.writeValueAsString(response))

          // docs
          docsRoot
            .restDoc(
              identifier = "updateUser200",
              description = "회원정보 수정 API",
            ) {
              request {
                header {
                  "Authorization" mean "Bearer token"
                  "Content-Type" mean "multipart/form-data"
                }
                path {
                  "userId" mean "사용자 아이디"
                }
                part {
                  "json" mean "회원정보 수정 요청 JSON"
                  "file" mean "프로필 이미지 파일"
                }
                part("json") {
                  "email" type Strings mean "이메일"
                  "username" type Strings mean "닉네임"
                  "password" type Strings mean "기존 비밀번호"
                  "newPassword" type Strings mean "새 비밀번호"
                }
              }
              response {
                body {
                  "data.email" type Strings mean "이메일"
                  "data.username" type Strings mean "닉네임"
                  "data.email" type Strings mean "이메일"
                }
              }
            }
        }

        it("회원정보 수정 실패 : 권한이 없는 토큰") {
          // given
          val userId = 1L
          val request =
            UpdateUserProfileRequest.builder()
              .email(TEST_EMAIL)
              .username(TEST_USERNAME)
              .password(TEST_PASSWORD)
              .newPassword(TEST_NEW_PASSWORD)
              .build()

          val fileResource = ClassPathResource(TEST_PROFILE_IMG)
          val file =
            MockMultipartFile(
              "image",
              fileResource.filename,
              MediaType.IMAGE_JPEG_VALUE,
              fileResource.inputStream,
            )

          val requestJson = objectMapper.writeValueAsString(request)

          val response =
            FailMessageResponse.builder().message(UserErrorCode.NOT_ALLOWED.message()).build()
          every { authClient.getTokenInfo(any()) } throws ServiceException(UserErrorCode.NOT_ALLOWED)

          val bodyBuilder =
            MultipartBodyBuilder()
          bodyBuilder.part("json", requestJson, MediaType.APPLICATION_JSON)
          fileResource.filename?.let {
            bodyBuilder.part("file", ByteArrayResource(file.bytes), MediaType.IMAGE_JPEG).filename(
              it,
            )
          }

          // when
          val result =
            webTestClient.patch()
              .uri("/api/v1/user/{userId}", userId)
              .header("Authorization", "Bearer token")
              .contentType(MediaType.MULTIPART_FORM_DATA)
              .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
              .exchange()

          // then
          val docsRoot =
            result
              .expectStatus().isForbidden
              .expectBody().json(objectMapper.writeValueAsString(response))

          // docs
          docsRoot
            .restDoc(
              identifier = "updateUser403",
              description = "회원정보 수정 API",
            ) {
              request {
                header {
                  "Content-Type" mean "multipart/form-data"
                  "Authorization" mean "Bearer token"
                }
                path {
                  "userId" mean "사용자 아이디"
                }
                part {
                  "json" mean "회원정보 수정 요청 JSON"
                  "file" mean "프로필 이미지 파일"
                }
                part("json") {
                  "email" type Strings mean "이메일"
                  "username" type Strings mean "닉네임"
                  "password" type Strings mean "기존 비밀번호"
                  "newPassword" type Strings mean "새 비밀번호"
                }
              }
              response {
                body {
                  "message" type Strings mean "에러 메시지"
                }
              }
            }
        }

        it("회원정보 수정 실패 : 서버 내부 오류") {
          // given
          val userId = 1L
          val token = "Bearer token"
          val request =
            UpdateUserProfileRequest.builder()
              .email(TEST_EMAIL)
              .username(TEST_USERNAME)
              .password(TEST_PASSWORD)
              .newPassword(TEST_NEW_PASSWORD)
              .build()

          val fileResource = ClassPathResource(TEST_PROFILE_IMG)
          val file =
            MockMultipartFile(
              "image",
              fileResource.filename,
              MediaType.IMAGE_JPEG_VALUE,
              fileResource.inputStream,
            )
          val requestJson = objectMapper.writeValueAsString(request)

          val response = FailMessageResponse.serverError
          every { authClient.getTokenInfo(token) } throws RuntimeException("서버 내부 오류")

          val bodyBuilder =
            MultipartBodyBuilder()
          bodyBuilder.part("json", requestJson, MediaType.APPLICATION_JSON)
          fileResource.filename?.let {
            bodyBuilder.part("file", ByteArrayResource(file.bytes), MediaType.IMAGE_JPEG).filename(
              it,
            )
          }

          // when
          val result =
            webTestClient.patch()
              .uri("/api/v1/user/{userId}", userId)
              .header("Authorization", "Bearer token")
              .contentType(MediaType.MULTIPART_FORM_DATA)
              .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
              .exchange()

          // then
          val docsRoot =
            result
              .expectStatus().isEqualTo(500)
              .expectBody().json(objectMapper.writeValueAsString(response))

          // docs
          docsRoot
            .restDoc(
              identifier = "updateUser500",
              description = "회원정보 수정 API",
            ) {
              request {
                header {
                  "Content-Type" mean "multipart/form-data"
                }
                path {
                  "userId" mean "사용자 아이디"
                }
                part {
                  "json" mean "회원정보 수정 요청 JSON"
                  "file" mean "프로필 이미지 파일"
                }
                part("json") {
                  "email" type Strings mean "이메일"
                  "username" type Strings mean "닉네임"
                  "password" type Strings mean "기존 비밀번호"
                  "newPassword" type Strings mean "새 비밀번호"
                }
              }
              response {
                body {
                  "message" type Strings mean "에러 메시지"
                }
              }
            }
        }
      }

      describe("프로필 조회 API") {
        it("조회 성공") {
          // given
          val userId = 1L
          val token = "Bearer test"
          val data =
            GetUserProfileResponse.builder()
              .userId(userId)
              .email(TEST_EMAIL)
              .username(TEST_USERNAME)
              .build()
          val response = ApiResponse(data = data)
          every { authClient.getTokenInfo(token) }.returns(
            ApiResponse(data = TokenInfo(TokenType.ACCESS, userId.toString())),
          )
          every { userService.getProfile(userId) } returns data

          // when
          val result =
            webTestClient.get()
              .uri("/api/v1/user/{userId}", userId)
              .header("Authorization", token)
              .exchange()

          // then
          val docsRoot =
            result
              .expectStatus().isOk
              .expectBody().json(objectMapper.writeValueAsString(response))

          // docs
          docsRoot
            .restDoc(
              identifier = "getUserProfile200",
              description = "프로필 조회 API",
            ) {
              request {
                path {
                  "userId" mean "사용자 아이디"
                }
                header {
                  "Authorization" mean "Bearer token"
                }
              }
              response {
                body {
                  "data.userId" type Strings mean "사용자 아이디"
                  "data.email" type Strings mean "이메일"
                  "data.username" type Strings mean "닉네임"
                }
              }
            }
        }

        it("조회 실패 : 권한이 없는 토큰") {
          // given
          val userId = 1L
          val token = "Bearer test"
          val response =
            FailMessageResponse.builder().message(UserErrorCode.NOT_ALLOWED.message()).build()
          every { authClient.getTokenInfo(token) } throws ServiceException(UserErrorCode.NOT_ALLOWED)

          // when
          val result =
            webTestClient.get()
              .uri("/api/v1/user/{userId}", userId)
              .header("Authorization", token)
              .exchange()

          // then
          val docsRoot =
            result
              .expectStatus().isForbidden
              .expectBody().json(objectMapper.writeValueAsString(response))

          // docs
          docsRoot
            .restDoc(
              identifier = "getUserProfile403",
              description = "프로필 조회 API",
            ) {
              request {
                path { "userId" mean "사용자 아이디" }
                header { "Authorization" mean "Bearer token" }
              }
              response {
                body { "message" type Strings mean "에러 메시지" }
              }
            }
        }

        it("조회 실패 : 서버 내부 오류") {
          // given
          val userId = 1L
          val token = "Bearer test"
          every { authClient.getTokenInfo(any()) } throws RuntimeException("서버 내부 오류")
          val response = FailMessageResponse.serverError

          // when
          val result =
            webTestClient.get()
              .uri("/api/v1/user/{userId}", userId)
              .header("Authorization", token)
              .exchange()

          // then
          val docsRoot =
            result
              .expectStatus().isEqualTo(500)
              .expectBody().json(objectMapper.writeValueAsString(response))

          // docs
          docsRoot
            .restDoc(
              identifier = "getUserProfile500",
              description = "프로필 조회 API",
            ) {
              request {
                path { "userId" mean "사용자 아이디" }
                header { "Authorization" mean "Bearer token" }
              }
              response { body { "message" type Strings mean "에러 메시지" } }
            }
        }
      }

      describe("탈퇴 API") {
        it("탈퇴 성공") {
          // given
          val userId = 1L
          val validationResponse = TokenValidationResponse(true, TokenType.ACCESS, userId.toString())
          every { authClient.getTokenInfo(any()) } returns
            ApiResponse(data = TokenInfo(TokenType.ACCESS, userId.toString()))
          every { userService.exitUser(userId) } returns true

          // when
          val result =
            webTestClient.delete()
              .uri("/api/v1/user/{userId}", userId)
              .header("Authorization", "Bearer token")
              .exchange()

          // then
          verify(exactly = 1) { authClient.getTokenInfo(any()) }
          val docsRoot =
            result
              .expectStatus().isOk
              .expectBody()

          // docs
          docsRoot
            .restDoc(
              identifier = "exitUser200",
              description = "탈퇴 API",
            ) {
              request {
                path { "userId" mean "사용자 아이디" }
                header {
                  "Authorization" mean "jwt 토큰 정보"
                }
              }
            }
        }

        it("탈퇴 실패 : 권한이 없는 토큰") {
          // given
          val userId = 1L
          val validationResponse = TokenValidationResponse(false, null, null)
          every { authClient.getTokenInfo(any()) } throws ServiceException(UserErrorCode.NOT_ALLOWED)

          // when
          val result =
            webTestClient.delete()
              .uri("/api/v1/user/{userId}", userId)
              .header("Authorization", "Bearer token")
              .exchange()

          // then
          verify(exactly = 1) { authClient.getTokenInfo(any()) }
          val docsRoot =
            result
              .expectStatus().isForbidden
              .expectBody()

          // docs
          docsRoot
            .restDoc(
              identifier = "exitUser403",
              description = "탈퇴 API",
            ) {
              request {
                path { "userId" mean "사용자 아이디" }
                header { "Authorization" mean "jwt 토큰 정보" }
              }
            }
        }

        it("탈퇴 실패 : 서버 내부 오류") {
          // given
          val userId = 1L
          val token = "Bearer token"
          every { authClient.getTokenInfo(token) } throws RuntimeException("서버 내부 오류")

          // when
          val result =
            webTestClient.delete()
              .uri("/api/v1/user/{userId}", userId)
              .header("Authorization", "Bearer token")
              .exchange()

          // then
          verify(exactly = 1) { authClient.getTokenInfo(any()) }
          val docsRoot =
            result
              .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
              .expectBody()

          // docs
          docsRoot
            .restDoc(
              identifier = "exitUser500",
              description = "탈퇴 API",
            ) {
              request {
                path { "userId" mean "사용자 아이디" }
                header { "Authorization" mean "jwt 토큰 정보" }
              }
              response {
                body { "message" type Strings mean "에러 메시지" }
              }
            }
        }
      }
    },
  ) {
  companion object {
    private const val TEST_EMAIL = "test@email.com"
    private const val TEST_PASSWORD = "tesT@1234"
    private const val TEST_NEW_PASSWORD = "tesT@1234!"
    private const val TEST_USERNAME = "testuser"
    private const val TEST_PROFILE_IMG = "/images/profileImg"
  }
}
