package kpring.chat.chat.api.v1

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.junit5.MockKExtension
import kpring.chat.chat.service.ChatService
import kpring.chat.global.CommonTest
import kpring.core.auth.client.AuthClient
import kpring.core.auth.dto.response.TokenInfo
import kpring.core.auth.enums.TokenType
import kpring.core.chat.chat.dto.response.ChatResponse
import kpring.core.global.dto.response.ApiResponse
import kpring.core.server.client.ServerClient
import kpring.server.config.CoreConfiguration
import kpring.test.restdoc.dsl.restDoc
import kpring.test.web.URLBuilder
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
import java.time.LocalDateTime

@WebMvcTest(controllers = [ChatController::class])
@ExtendWith(RestDocumentationExtension::class)
@ExtendWith(SpringExtension::class)
@ExtendWith(MockKExtension::class)
@Import(CoreConfiguration::class)
class ChatControllerTest(
  private val om: ObjectMapper,
  webContext: WebApplicationContext,
  @MockkBean val chatService: ChatService,
  @MockkBean val serverClient: ServerClient,
  @MockkBean val authClient: AuthClient,
) : DescribeSpec({

  val restDocument = ManualRestDocumentation()
  val webTestClient: WebTestClient = MockMvcWebTestClient.bindToApplicationContext(webContext).configureClient()
    .baseUrl("http://localhost:8081")
    .filter(
      WebTestClientRestDocumentation.documentationConfiguration(restDocument).operationPreprocessors()
        .withRequestDefaults(Preprocessors.prettyPrint()).withResponseDefaults(Preprocessors.prettyPrint()),
    )
    .build()

  beforeSpec { restDocument.beforeTest(this.javaClass, "chat controller") }

  afterSpec { restDocument.afterTest() }

  describe("GET /api/v1/chat/server/{serverId} : getServerChats api test") {

    val url = "/api/v1/chat/server/{serverId}"
    it("getServerChats api test") {

      // Given
      val serverId = "test_server_id"
      val data = listOf(ChatResponse(serverId, false, LocalDateTime.now(), "sad"))

      every { authClient.getTokenInfo(any()) } returns ApiResponse(
        data = TokenInfo(
          type = TokenType.ACCESS, userId = CommonTest.TEST_USER_ID
        )
      )

      every { serverClient.verifyIfJoined(any(), any()) } returns true

      every { chatService.getChatsByServer(serverId, CommonTest.TEST_USER_ID, 1) } returns data

      // When
      val result = webTestClient.get().uri(
        URLBuilder(url).query("page",1).build(),serverId)
        .header("Authorization", "Bearer mock_token")
        .exchange()

      val docs = result.expectStatus().isOk.expectBody().json(om.writeValueAsString((ApiResponse(data=data))))

      // Then
      docs.restDoc(
        identifier = "get_server_chats_200",
        description = "채팅 조회 api",
      ) {
        request {
          path { "serverId" mean "서버 id" }
          query { "page" mean "page number" }
        }

        response {
          body {
            "data[].id" type "String" mean "ServerChat ID"
            "data[].isEdited" type "Boolean" mean "If the chat is edited"
            "data[].sentAt" type "LocalDateTime" mean "When the chat is sent"
            "data[].content" type "String" mean "The content of the chat"
          }
        }
      }
    }
  }
})
