package kpring.chat.chat.api.v1

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.junit5.MockKExtension
import kpring.chat.chat.service.ChatService
import kpring.chat.global.ChatRoomTest
import kpring.chat.global.CommonTest
import kpring.core.auth.client.AuthClient
import kpring.core.auth.dto.response.TokenInfo
import kpring.core.auth.enums.TokenType
import kpring.core.chat.chat.dto.request.ChatType
import kpring.core.chat.chat.dto.request.CreateChatRequest
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

@WebMvcTest(controllers = [ChatController::class])
@ExtendWith(RestDocumentationExtension::class)
@ExtendWith(SpringExtension::class)
@ExtendWith(MockKExtension::class)
class ChatControllerTest(
  private val om: ObjectMapper,
  webContext: WebApplicationContext,
  @MockkBean val chatService: ChatService,
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

    beforeSpec { restDocument.beforeTest(this.javaClass, "chat controller") }

    afterSpec { restDocument.afterTest() }

    describe("POST /api/v1/chat : createChat api test") {

      val url = "/api/v1/chat"
      it("createChat api test") {

        // Given
        val content = "create_chat_test"
        val id = ChatRoomTest.TEST_ROOM_ID
        val type = ChatType.Room
        val request = CreateChatRequest(content = content, type = type, id = id)

        val data = true

        every { authClient.getTokenInfo(any()) } returns
          ApiResponse(
            data =
              TokenInfo(
                type =
                  TokenType.ACCESS,
                userId = CommonTest.TEST_USER_ID,
              ),
          )

        every { chatService.createRoomChat(request, CommonTest.TEST_USER_ID) } returns data

        // When
        val result =
          webTestClient.post().uri(URLBuilder(url).build())
            .header("Authorization", "Bearer mock_token")
            .bodyValue(request)
            .exchange()

        val docs =
          result
            .expectStatus()
            .isCreated()
            .expectBody()
            .json(om.writeValueAsString(ApiResponse(data = null, status = 201)))

        // Then
        docs.restDoc(
          identifier = "create_chat_201",
          description = "채팅 생성 api",
        ) {
          response {
            body {
              "status" type JsonDataType.Integers mean "successfully created a chat"
            }
          }
        }
      }
    }
  })
