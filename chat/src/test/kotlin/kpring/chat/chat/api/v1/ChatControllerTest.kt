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
import kpring.core.server.dto.ServerSimpleInfo
import kpring.test.restdoc.dsl.restDoc
import kpring.test.restdoc.json.JsonDataType
import kpring.test.web.URLBuilder
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.ResponseEntity
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
class ChatControllerTest(
  private val om: ObjectMapper,
  webContext: WebApplicationContext,
  @MockkBean val chatService: ChatService,
  @MockkBean val serverClient: ServerClient,
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

    describe("GET /api/v1/chat : getServerChats api test") {

      val url = "/api/v1/chat"
      it("getServerChats api test") {

        // Given
        val serverId = "test_server_id"
        val data =
          listOf(
            ChatResponse(
              serverId,
              false,
              LocalDateTime.now().toString(),
              "sad",
            ),
          )
        val serverList =
          listOf(
            ServerSimpleInfo(
              id = serverId,
              name = "test_server_name",
              bookmarked = true,
            ),
          )

        every { authClient.getTokenInfo(any()) } returns
          ApiResponse(
            data =
              TokenInfo(
                type = TokenType.ACCESS, userId = CommonTest.TEST_USER_ID,
              ),
          )

        every { serverClient.getServerList(any(), any()) } returns
          ResponseEntity.ok().body(ApiResponse(data = serverList))

        every {
          chatService.getServerChats(
            serverId,
            CommonTest.TEST_USER_ID,
            1,
            serverList,
          )
        } returns data

        // When
        val result =
          webTestClient.get().uri(
            URLBuilder(url).query("id", serverId).query("type", "Server").query("page", 1).build(),
          )
            .header("Authorization", "Bearer mock_token")
            .exchange()

        val docs = result.expectStatus().isOk.expectBody().json(om.writeValueAsString(ApiResponse(data = data)))

        // Then
        docs.restDoc(
          identifier = "get_server_chats_200",
          description = "서버 채팅 조회 api",
        ) {

          request {
            query {

              "type" mean "Server / Room"
              "id" mean "서버 ID"
              "page" mean "페이지 번호"
            }
          }

          response {
            body {
              "status" type JsonDataType.Integers mean "상태 코드"
              "data[].id" type JsonDataType.Strings mean "서버 ID"
              "data[].isEdited" type JsonDataType.Booleans mean "메시지가 수정되었는지 여부"
              "data[].sentAt" type JsonDataType.Strings mean "메시지 생성 시간"
              "data[].content" type JsonDataType.Strings mean "메시지 내용"
            }
          }
        }
      }
    }

    describe("GET /api/v1/chat : getRoomChats api test") {

      val url = "/api/v1/chat"
      it("getRoomChats api test") {

        // Given
        val roomId = "test_room_id"
        val data =
          listOf(
            ChatResponse(
              roomId,
              false,
              LocalDateTime.now().toString(),
              "sad",
            ),
          )

        every { authClient.getTokenInfo(any()) } returns
          ApiResponse(
            data =
              TokenInfo(
                type = TokenType.ACCESS, userId = CommonTest.TEST_USER_ID,
              ),
          )

        every {
          chatService.getRoomChats(
            roomId,
            CommonTest.TEST_USER_ID,
            1,
          )
        } returns data

        // When
        val result =
          webTestClient.get().uri(
            URLBuilder(url).query("id", roomId).query("type", "Room").query("page", 1).build(),
          )
            .header("Authorization", "Bearer mock_token")
            .exchange()

        val docs = result.expectStatus().isOk.expectBody().json(om.writeValueAsString(ApiResponse(data = data)))

        // Then
        docs.restDoc(
          identifier = "get_room_chats_200",
          description = "채팅방 채팅 조회 api",
        ) {
          request {
            query {
              "type" mean "Server / Room"
              "id" mean "채팅방 ID"
              "page" mean "페이지 번호"
            }
          }

          response {
            body {
              "status" type JsonDataType.Integers mean "상태 코드"
              "data[].id" type JsonDataType.Strings mean "채팅방 ID"
              "data[].isEdited" type JsonDataType.Booleans mean "메시지가 수정되었는지 여부"
              "data[].sentAt" type JsonDataType.Strings mean "메시지 생성 시간"
              "data[].content" type JsonDataType.Strings mean "메시지 내용"
            }
          }
        }
      }
    }
  })
