package kpring.server.adapter.input.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import kpring.core.auth.client.AuthClient
import kpring.core.global.dto.response.ApiResponse
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
})
