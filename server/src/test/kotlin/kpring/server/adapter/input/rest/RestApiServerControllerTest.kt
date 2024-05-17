package kpring.server.adapter.input.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import kpring.core.auth.client.AuthClient
import kpring.core.global.dto.response.ApiResponse
import kpring.core.server.dto.ServerInfo
import kpring.core.server.dto.request.AddUserAtServerRequest
import kpring.core.server.dto.request.CreateServerRequest
import kpring.core.server.dto.response.CreateServerResponse
import kpring.server.application.service.ServerService
import kpring.test.restdoc.dsl.restDoc
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import org.springframework.web.context.WebApplicationContext

@WebMvcTest(controllers = [RestApiServerController::class])
@ExtendWith(
  value = [
    MockKExtension::class,
  ],
)
class RestApiServerControllerTest(
  private val objectMapper: ObjectMapper,
  webContext: WebApplicationContext,
  @MockkBean val serverService: ServerService,
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

  beforeSpec { restDocument.beforeTest(this.javaClass, "user controller") }

  afterSpec { restDocument.afterTest() }

  afterTest { clearMocks(authClient) }

  describe("POST /api/v1/server : createServer api test") {
    val url = "/api/v1/server"
    it("요청 성공시") {
      // given
      val request = CreateServerRequest(serverName = "test server")
      val data = CreateServerResponse(serverId = "1", serverName = request.serverName)
      every { serverService.createServer(request) } returns data

      // when
      val result = webTestClient.post()
        .uri(url)
        .header("Authorization", "Bearer mock_token")
        .bodyValue(request)
        .exchange()

      // then
      val docs = result
        .expectStatus().isOk
        .expectBody()
        .json(objectMapper.writeValueAsString(ApiResponse(data = data)))

      // docs
      docs.restDoc(
        identifier = "create_server_200",
        description = "서버 생성 api",
      ) {
        request {
          header { "Authorization" mean "jwt access token" }
          body {
            "serverName" type "String" mean "생성할 서버의 이름"
          }
        }

        response {
          body {
            "data.serverId" type "String" mean "서버 id"
            "data.serverName" type "String" mean "생성된 서버 이름"
          }
        }
      }
    }
  }

  describe("GET /api/v1/server/{serverId}: 서버 조회 api test") {

    val url = "/api/v1/server/{serverId}"
    it("요청 성공시") {
      // given
      val serverId = "test_server_id"
      val data = ServerInfo(id = serverId, name = "test_server", users = emptyList())
      every { serverService.getServerInfo(serverId) } returns data

      // when
      val result = webTestClient.get()
        .uri(url, serverId)
        .exchange()

      // then
      val docs = result
        .expectStatus().isOk
        .expectBody()
        .json(objectMapper.writeValueAsString(ApiResponse(data = data)))

      // docs
      docs.restDoc(
        identifier = "get_server_info_200",
        description = "서버 단건 조회 api",
      ) {
        request {
          path { "serverId" mean "서버 id" }
        }

        response {
          body {
            "data.id" type "String" mean "서버 id"
            "data.name" type "String" mean "생성된 서버 이름"
            "data.users" type "Array" mean "서버에 가입된 유저 목록"
          }
        }
      }
    }
  }

  describe("PUT /api/v1/server/{serverId}/user : 서버 가입 api test") {
    val url = "/api/v1/server/{serverId}/user"
    it("요청 성공시") {
      // given
      val request =
        AddUserAtServerRequest(userId = "userId", userName = "test", profileImage = "test")
      justRun { serverService.addInvitedUser("test_server_id", request) }

      // when
      val result = webTestClient.put()
        .uri(url, "test_server_id")
        .header("Authorization", "Bearer mock_token")
        .bodyValue(request)
        .exchange()

      // then
      val docs = result
        .expectStatus().isOk
        .expectBody()

      // docs
      docs.restDoc(
        identifier = "add_invited_user_200",
        description = "서버 가입 api",
      ) {
        request {
          header { "Authorization" mean "jwt access token" }
          path { "serverId" mean "서버 id" }
          body {
            "userId" type "String" mean "가입할 유저 id"
            "userName" type "String" mean "가입할 유저 이름"
            "profileImage" type "String" mean "가입할 유저 프로필 이미지"
          }
        }
      }
    }
  }

  describe("PUT /api/v1/server/{serverId}/invitation/{userId} : 서버 초대 api test") {
    val url = "/api/v1/server/{serverId}/invitation/{userId}"
    it("요청 성공시") {
      // given
      justRun { serverService.inviteUser(eq("test_server_id"), any()) }

      // when
      val result = webTestClient.put()
        .uri(url, "test_server_id", "userId")
        .header("Authorization", "Bearer mock_token")
        .exchange()

      // then
      val docs = result
        .expectStatus().isOk
        .expectBody()

      // docs
      docs.restDoc(
        identifier = "invite_user_200",
        description = "서버 초대 api",
      ) {
        request {
          header { "Authorization" mean "jwt access token" }
          path {
            "serverId" mean "서버 id"
            "userId" mean "초대할 유저 id"
          }
        }
      }
    }
  }
})
