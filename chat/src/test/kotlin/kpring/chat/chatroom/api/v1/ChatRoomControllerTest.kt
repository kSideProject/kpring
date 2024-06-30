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
import kpring.chat.global.config.TestMongoConfig
import kpring.core.auth.client.AuthClient
import kpring.core.auth.dto.response.TokenInfo
import kpring.core.auth.enums.TokenType
import kpring.core.chat.chat.dto.response.InvitationResponse
import kpring.core.global.dto.response.ApiResponse
import kpring.test.restdoc.dsl.restDoc
import kpring.test.restdoc.json.JsonDataType
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
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
@Import(TestMongoConfig::class)
class ChatRoomControllerTest(
  private val om: ObjectMapper,
  webContext: WebApplicationContext,
  @MockkBean val chatRoomService: ChatRoomService,
  @MockkBean val authClient: AuthClient,
) : DescribeSpec({

    val restDocument = ManualRestDocumentation()
    val webTestClient: WebTestClient =
      MockMvcWebTestClient.bindToApplicationContext(webContext).configureClient().baseUrl("http://localhost:8081").filter(
        WebTestClientRestDocumentation.documentationConfiguration(restDocument).operationPreprocessors()
          .withRequestDefaults(Preprocessors.prettyPrint()).withResponseDefaults(Preprocessors.prettyPrint()),
      ).build()

    beforeSpec { restDocument.beforeTest(this.javaClass, "chat controller") }

    afterSpec { restDocument.afterTest() }

    describe("GET /api/v1/chatroom/{chatRoomId}/invite : getChatRoomInvitation api test") {

      val url = "/api/v1/chatroom/{chatRoomId}/invite"
      it("getChatRoomInvitation api test") {

        // Given
        val chatRoomId = ChatRoomTest.TEST_ROOM_ID
        val userId = CommonTest.TEST_USER_ID
        val key = "62e9df6b-13cb-4673-a6fe-8566451b7f15"
        val data = InvitationResponse(key)

        every { authClient.getTokenInfo(any()) } returns
          ApiResponse(
            data =
              TokenInfo(
                type = TokenType.ACCESS, userId = CommonTest.TEST_USER_ID,
              ),
          )

        every {
          chatRoomService.getChatRoomInvitation(
            chatRoomId,
            userId,
          )
        } returns data

        // When
        val result = webTestClient.get().uri(url, chatRoomId).header("Authorization", "Bearer mock_token").exchange()

        // Then
        val docs = result.expectStatus().isOk.expectBody().json(om.writeValueAsString(ApiResponse(data = data)))

        docs.restDoc(
          identifier = "getChatRoomInvitation_200",
          description = "채팅방 참여코드를 위한 key값을 반환하는 api",
        ) {
          request {
            path {
              "chatRoomId" mean "채팅방 참여코드를 발급할 채팅방 Id"
            }
          }

          response {
            body {
              "data.code" type JsonDataType.Strings mean "참여 코드"
            }
          }
        }
      }
    }

    describe("GET /api/v1//chatroom/{code}/join : joinChatRoom api test") {

      val url = "/api/v1/chatroom/{code}/join"
      it("joinChatRoom api test") {

        // Given
        val chatRoomId = ChatRoomTest.TEST_ROOM_ID
        val userId = CommonTest.TEST_USER_ID
        val code = "666fcd76027b2432e4b49a0f"
        val data = true

        every { authClient.getTokenInfo(any()) } returns
          ApiResponse(
            data =
              TokenInfo(
                type = TokenType.ACCESS, userId = CommonTest.TEST_USER_ID,
              ),
          )

        every {
          chatRoomService.joinChatRoom(
            code,
            userId,
          )
        } returns data

        // When
        val result = webTestClient.patch().uri(url, code).header("Authorization", "Bearer mock_token").exchange()

        // Then
        val docs = result.expectStatus().isOk.expectBody().json(om.writeValueAsString(ApiResponse<Nothing>(status = 200)))

        docs.restDoc(
          identifier = "joinChatRoom_200",
          description = "코드로 채팅방에 참여하는 api",
        ) {
          request {
            path {
              "code" mean "채팅방 참여코드"
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
