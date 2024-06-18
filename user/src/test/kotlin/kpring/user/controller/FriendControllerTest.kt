package kpring.user.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.junit5.MockKExtension
import kpring.core.auth.client.AuthClient
import kpring.core.auth.dto.response.TokenInfo
import kpring.core.auth.enums.TokenType
import kpring.core.global.dto.response.ApiResponse
import kpring.core.global.exception.ServiceException
import kpring.test.restdoc.dsl.restDoc
import kpring.test.restdoc.json.JsonDataType
import kpring.test.restdoc.json.JsonDataType.Strings
import kpring.user.dto.response.AddFriendResponse
import kpring.user.dto.response.FailMessageResponse
import kpring.user.dto.response.GetFriendRequestResponse
import kpring.user.dto.response.GetFriendRequestsResponse
import kpring.user.exception.UserErrorCode
import kpring.user.global.AuthValidator
import kpring.user.global.CommonTest
import kpring.user.service.FriendServiceImpl
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import org.springframework.web.context.WebApplicationContext

@WebMvcTest(controllers = [FriendController::class])
@ExtendWith(value = [MockKExtension::class])
internal class FriendControllerTest(
  private val objectMapper: ObjectMapper,
  webContext: WebApplicationContext,
  @MockkBean val friendService: FriendServiceImpl,
  @MockkBean val authValidator: AuthValidator,
  @MockkBean val authClient: AuthClient,
) : DescribeSpec({

    val restDocument = ManualRestDocumentation()
    val webTestClient =
      MockMvcWebTestClient.bindToApplicationContext(webContext)
        .configureClient()
        .filter(
          WebTestClientRestDocumentation.documentationConfiguration(restDocument)
            .operationPreprocessors()
            .withRequestDefaults(Preprocessors.prettyPrint())
            .withResponseDefaults(Preprocessors.prettyPrint()),
        )
        .build()

    beforeSpec { restDocument.beforeTest(this.javaClass, "friend controller") }

    afterSpec { restDocument.afterTest() }

    describe("친구신청 API") {
      it("친구신청 성공") {
        // given
        val data = AddFriendResponse(friendId = CommonTest.TEST_FRIEND_ID)
        val response = ApiResponse(data = data)

        every { authClient.getTokenInfo(any()) }.returns(
          ApiResponse(data = TokenInfo(TokenType.ACCESS, CommonTest.TEST_USER_ID.toString())),
        )
        every {
          authValidator.checkIfAccessTokenAndGetUserId(any())
        } returns CommonTest.TEST_USER_ID.toString()
        every { authValidator.checkIfUserIsSelf(any(), any()) } returns Unit
        every {
          friendService.addFriend(CommonTest.TEST_USER_ID, CommonTest.TEST_FRIEND_ID)
        } returns data

        // when
        val result =
          webTestClient.post()
            .uri(
              "/api/v1/user/{userId}/friend/{friendId}",
              CommonTest.TEST_USER_ID,
              CommonTest.TEST_FRIEND_ID,
            )
            .header("Authorization", CommonTest.TEST_TOKEN)
            .exchange()

        // then
        val docsRoot =
          result
            .expectStatus().isOk
            .expectBody().json(objectMapper.writeValueAsString(response))

        // docs
        docsRoot
          .restDoc(
            identifier = "addFriend200",
            description = "친구신청 API",
          ) {
            request {
              path {
                "userId" mean "사용자 아이디"
                "friendId" mean "친구신청을 받은 사용자의 아이디"
              }
              header { "Authorization" mean "Bearer token" }
            }
            response {
              body {
                "data.friendId" type Strings mean "친구신청을 받은 사용자의 아이디"
              }
            }
          }
      }

      it("친구신청 실패 : 권한이 없는 토큰") {
        // given
        val response =
          FailMessageResponse.builder().message(UserErrorCode.NOT_ALLOWED.message()).build()
        every { authClient.getTokenInfo(any()) } throws ServiceException(UserErrorCode.NOT_ALLOWED)

        // when
        val result =
          webTestClient.post()
            .uri(
              "/api/v1/user/{userId}/friend/{friendId}",
              CommonTest.TEST_USER_ID,
              CommonTest.TEST_FRIEND_ID,
            )
            .header("Authorization", CommonTest.TEST_TOKEN)
            .exchange()

        // then
        val docsRoot =
          result
            .expectStatus().isForbidden
            .expectBody().json(objectMapper.writeValueAsString(response))

        // docs
        docsRoot
          .restDoc(
            identifier = "addFriend403",
            description = "친구신청 API",
          ) {
            request {
              path {
                "userId" mean "사용자 아이디"
                "friendId" mean "친구신청을 받은 사용자의 아이디"
              }
              header { "Authorization" mean "Bearer token" }
            }
            response {
              body {
                body { "message" type Strings mean "에러 메시지" }
              }
            }
          }
      }

      it("친구신청 실패 : 서버 내부 오류") {
        // given
        every { authClient.getTokenInfo(any()) } throws RuntimeException("서버 내부 오류")
        val response = FailMessageResponse.serverError

        // when
        val result =
          webTestClient.post()
            .uri(
              "/api/v1/user/{userId}/friend/{friendId}",
              CommonTest.TEST_USER_ID,
              CommonTest.TEST_FRIEND_ID,
            )
            .header("Authorization", CommonTest.TEST_TOKEN)
            .exchange()

        // then
        val docsRoot =
          result
            .expectStatus().isEqualTo(500)
            .expectBody().json(objectMapper.writeValueAsString(response))

        // docs
        docsRoot
          .restDoc(
            identifier = "addFriend500",
            description = "친구신청 API",
          ) {
            request {
              path {
                "userId" mean "사용자 아이디"
                "friendId" mean "친구신청을 받은 사용자의 아이디"
              }
              header { "Authorization" mean "Bearer token" }
            }
            response {
              body {
                "message" type Strings mean "에러 메시지"
              }
            }
          }
      }
    }

    describe("친구신청 조회 API") {
      it("친구신청 조회 성공") {
        // given
        val friendRequest =
          GetFriendRequestResponse(
            friendId = CommonTest.TEST_FRIEND_ID,
            username = CommonTest.TEST_FRIEND_USERNAME,
          )
        val data =
          GetFriendRequestsResponse(
            userId = CommonTest.TEST_USER_ID,
            friendRequests = mutableListOf(friendRequest),
          )
        val response = ApiResponse(data = data)

        every { authClient.getTokenInfo(any()) }.returns(
          ApiResponse(data = TokenInfo(TokenType.ACCESS, CommonTest.TEST_USER_ID.toString())),
        )
        every {
          authValidator.checkIfAccessTokenAndGetUserId(any())
        } returns CommonTest.TEST_USER_ID.toString()
        every { authValidator.checkIfUserIsSelf(any(), any()) } returns Unit
        every {
          friendService.getFriendRequests(CommonTest.TEST_USER_ID)
        } returns data

        // when
        val result =
          webTestClient.get()
            .uri(
              "/api/v1/user/{userId}/requests",
              CommonTest.TEST_USER_ID,
            )
            .header("Authorization", CommonTest.TEST_TOKEN)
            .exchange()

        // then
        val docsRoot =
          result
            .expectStatus().isOk
            .expectBody().json(objectMapper.writeValueAsString(response))

        // docs
        docsRoot
          .restDoc(
            identifier = "getFriendRequests200",
            description = "친구신청 조회 API",
          ) {
            request {
              path {
                "userId" mean "사용자 아이디"
              }
              header { "Authorization" mean "Bearer token" }
            }
            response {
              body {
                "data.userId" type Strings mean "사용자 아이디"
                "data.friendRequests" type JsonDataType.Arrays mean "친구신청한 사용자 리스트"
                "data.friendRequests[].friendId" type Strings mean "친구신청한 사용자 아이디"
                "data.friendRequests[].username" type Strings mean "친구신청한 사용자 닉네임"
              }
            }
          }
      }
      it("친구신청 조회 실패 : 권한이 없는 토큰") {
        // given
        val response =
          FailMessageResponse.builder().message(UserErrorCode.NOT_ALLOWED.message()).build()
        every { authClient.getTokenInfo(any()) } throws ServiceException(UserErrorCode.NOT_ALLOWED)

        // when
        val result =
          webTestClient.get()
            .uri(
              "/api/v1/user/{userId}/requests",
              CommonTest.TEST_USER_ID,
            )
            .header("Authorization", CommonTest.TEST_TOKEN)
            .exchange()

        // then
        val docsRoot =
          result
            .expectStatus().isForbidden
            .expectBody().json(objectMapper.writeValueAsString(response))

        // docs
        docsRoot
          .restDoc(
            identifier = "getFriendRequests403",
            description = "친구신청 조회 API",
          ) {
            request {
              path {
                "userId" mean "사용자 아이디"
              }
              header { "Authorization" mean "Bearer token" }
            }
            response {
              body {
                "message" type Strings mean "에러 메시지"
              }
            }
          }
      }
      it("친구신청 조회 실패 : 서버 내부 오류") {
        // given
        val response = FailMessageResponse.serverError
        every { authClient.getTokenInfo(any()) } throws RuntimeException("서버 내부 오류")

        // when
        val result =
          webTestClient.get()
            .uri(
              "/api/v1/user/{userId}/requests",
              CommonTest.TEST_USER_ID,
            )
            .header("Authorization", CommonTest.TEST_TOKEN)
            .exchange()

        // then
        val docsRoot =
          result
            .expectStatus().isEqualTo(500)
            .expectBody().json(objectMapper.writeValueAsString(response))

        // docs
        docsRoot
          .restDoc(
            identifier = "getFriendRequests500",
            description = "친구신청 조회 API",
          ) {
            request {
              path {
                "userId" mean "사용자 아이디"
              }
              header { "Authorization" mean "Bearer token" }
            }
            response {
              body {
                "message" type Strings mean "에러 메시지"
              }
            }
          }
      }
    }
  })
