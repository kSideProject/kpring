package kpring.server.adapter.input.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.junit5.MockKExtension
import kpring.core.auth.client.AuthClient
import kpring.core.global.dto.response.ApiResponse
import kpring.core.server.dto.CategoryInfo
import kpring.server.application.service.ServerService
import kpring.server.config.CoreConfiguration
import kpring.test.restdoc.dsl.restDoc
import kpring.test.web.MvcWebTestClientDescribeSpec
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.web.context.WebApplicationContext

@Import(CoreConfiguration::class)
@WebMvcTest(
  controllers = [
    RestApiServerController::class,
    CategoryController::class,
  ],
)
@ExtendWith(
  value = [
    MockKExtension::class,
  ],
)
class CategoryControllerTest(
  private val om: ObjectMapper,
  webContext: WebApplicationContext,
  @MockkBean val serverService: ServerService,
  @MockkBean val authClient: AuthClient,
) : MvcWebTestClientDescribeSpec(
    "Rest api server category controller test",
    webContext,
    { client ->
      describe("GET /api/v1/category") {
        it("200 : 성공 케이스") {
          // given
          val data =
            listOf(
              CategoryInfo("카테고리1"),
              CategoryInfo("카테고리2"),
            )
          // when
          val result =
            client.get()
              .uri("/api/v1/category")
              .exchange()

          // then
          val docs =
            result
              .expectStatus().isOk
              .expectBody().json(om.writeValueAsString(ApiResponse(data = data)))

          // docs
          docs.restDoc(
            identifier = "get-categories",
            description = "서버 카테고리 목록 조회",
          ) {
          }
        }
      }
    },
  )
