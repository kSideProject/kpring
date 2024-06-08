package kpring.chat.chat.api.v1

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.junit5.MockKExtension
import kpring.chat.chatroom.api.v1.ChatRoomController
import kpring.chat.chatroom.service.ChatRoomService
import kpring.chat.global.ChatRoomTest
import kpring.chat.global.CommonTest
import kpring.core.auth.client.AuthClient
import kpring.core.auth.dto.response.TokenInfo
import kpring.core.auth.enums.TokenType
import kpring.core.global.dto.response.ApiResponse
import kpring.test.restdoc.dsl.restDoc
import kpring.test.restdoc.json.JsonDataType
import kpring.test.web.URLBuilder
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import org.springframework.web.context.WebApplicationContext

@WebMvcTest(controllers = [ChatRoomController::class])
@ExtendWith(RestDocumentationExtension::class)
@ExtendWith(SpringExtension::class)
@ExtendWith(MockKExtension::class)
class ChatRoomControllerTest(
  private val om: ObjectMapper,
  webContext: WebApplicationContext,
  @MockkBean val chatRoomService: ChatRoomService,
  @MockkBean val authClient: AuthClient,
) : DescribeSpec({

    val restDocument = ManualRestDocumentation()
    val webTestClient: WebTestClient =
      MockMvcWebTestClient.bindToApplicationContext(webContext).configureClient()
        .baseUrl("http://localhost:8081")
        .filter(
          WebTestClientRestDocumentation.documentationConfiguration(restDocument).operationPreprocessors()
            .withRequestDefaults(Preprocessors.prettyPrint()).withResponseDefaults(Preprocessors.prettyPrint()),
        )
        .build()

    beforeSpec { restDocument.beforeTest(this.javaClass, "chatroom controller") }

    afterSpec { restDocument.afterTest() }

    describe("PATCH /api/v1/chatroom : inviteToChatRoomByUserId api test") {

      val url = "/api/v1/chatroom/{chatRoomId}/invite/{userId}"
      it("inviteToChatRoomByUserId api test") {

        // Given
        val userId = CommonTest.TEST_ANOTHER_USER_ID
        val inviterId = CommonTest.TEST_USER_ID
        val chatRoomId = ChatRoomTest.TEST_ROOM_ID
        val data = true

        every { authClient.getTokenInfo(any()) } returns
          ApiResponse(
            data =
              TokenInfo(
                type = TokenType.ACCESS, userId = CommonTest.TEST_USER_ID,
              ),
          )

        every {
          chatRoomService.inviteToChatRoomByUserIdWithLock(
            userId,
            inviterId,
            chatRoomId,
          )
        } returns data

        // When
        val result =
          webTestClient.patch().uri(
            URLBuilder(url)
              .build(),
            chatRoomId,
            userId,
          )
            .header("Authorization", "Bearer mock_token")
            .exchange()

        val docs =
          result
            .expectStatus()
            .isOk
            .expectBody()
            .json(om.writeValueAsString(ApiResponse<Nothing>(status = 201)))

        // Then
        docs.restDoc(
          identifier = "invite_to_chatroom_by_user_id_201",
          description = "채팅방에 user id로 초대하기 api",
        ) {
          request {
            path {
              "chatRoomId" mean "채팅방 id"
              "userId" mean "초대받는 user id"
            }
          }

          response {
            body {
              "status" type JsonDataType.Integers mean "상태 코드"
            }
          }
        }
      }
    }
  })
