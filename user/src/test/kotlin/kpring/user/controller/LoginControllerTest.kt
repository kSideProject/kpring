package kpring.user.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.FeatureSpec
import io.mockk.every
import io.mockk.junit5.MockKExtension
import kpring.test.restdoc.dsl.restDoc
import kpring.user.dto.request.LoginRequest
import kpring.user.dto.request.LogoutRequest
import kpring.user.dto.response.LoginResponse
import kpring.user.service.LoginService
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import org.springframework.web.context.WebApplicationContext

@WebMvcTest(controllers = [LoginController::class])
@ExtendWith(value = [MockKExtension::class])
class LoginControllerTest(
  val context: WebApplicationContext,
  val objectMapper: ObjectMapper,
  @MockkBean val loginService: LoginService,
) : FeatureSpec({

    val restDocumentation = ManualRestDocumentation()
    val webTestClient: WebTestClient =
      MockMvcWebTestClient.bindToApplicationContext(context)
        .configureClient()
        .filter(
          documentationConfiguration(restDocumentation)
            .operationPreprocessors()
            .withRequestDefaults(prettyPrint())
            .withResponseDefaults(prettyPrint()),
        )
        .build()

    beforeSpec { restDocumentation.beforeTest(javaClass, "login controller") }
    afterSpec { restDocumentation.afterTest() }

    feature("API : login API") {
      scenario("200 OK 로그인 성공") {
        // given
        val request = LoginRequest.builder().email("test@email.com").build()
        val response = LoginResponse.builder().accessToken("accessToken").refreshToken("refreshToken").build()
        every { loginService.login(request) } returns response

        // when
        val result =
          webTestClient.post().uri("/api/v1/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()

        // then
        val document =
          result.expectStatus().isOk
            .expectBody().json(objectMapper.writeValueAsString(response))

        // docs
        document.restDoc("login200", "로그인 API") {
          request {
            body { "email" type "String" mean "email" }
          }
          response {
            body {
              "accessToken" type "String" mean "accessToken"
              "refreshToken" type "String" mean "refreshToken"
            }
          }
        }
      }

      scenario("400 BAD_REQUEST 로그인 실패") {
        // given
        val request = LoginRequest.builder().email("test@gmail.com").build()
        every { loginService.login(request) } throws IllegalArgumentException("Invalid email")

        // when
        val result =
          webTestClient.post().uri("/api/v1/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()

        // then
        val document =
          result
            .expectStatus().isBadRequest
            .expectBody()

        // docs
        document.restDoc("login400", "로그인 API") {
          request {
            body { "email" type "String" mean "email" }
          }
        }
      }

      scenario("500 INTERNAL_SERVER_ERROR 로그인 실패") {
        // given
        val request = LoginRequest.builder().email("test@naver.com").build()
        every { loginService.login(request) } throws RuntimeException("Internal server error")

        // when
        val result =
          webTestClient.post().uri("/api/v1/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()

        // then
        val document =
          result
            .expectStatus().is5xxServerError
            .expectBody()

        // docs
        document.restDoc("login500", "로그인 API") {
          request {
            body { "email" type "String" mean "email" }
          }
        }
      }
    }

    feature("API : logout API") {
      scenario("200 OK 로그아웃 성공") {
        // given
        val request = LogoutRequest.builder().accessToken("accessToken").refreshToken("refreshToken").build()
        every { loginService.logout(request) } returns Unit

        // when
        val result =
          webTestClient.post().uri("/api/v1/logout")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()

        // then
        val document =
          result.expectStatus().isOk
            .expectBody()

        // docs
        document.restDoc("logout200", "로그아웃 API") {
          request {
            body {
              "accessToken" type "String" mean "accessToken"
              "refreshToken" type "String" mean "refreshToken"
            }
          }
        }
      }

      scenario("400 BAD_REQUEST 로그아웃 실패") {
        // given
        val request = LogoutRequest.builder().accessToken("accessToken").refreshToken("refreshToken").build()
        every { loginService.logout(request) } throws IllegalArgumentException("Invalid token")

        // when
        val result =
          webTestClient.post().uri("/api/v1/logout")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()

        // then
        val document =
          result.expectStatus().isBadRequest
            .expectBody()

        // docs
        document.restDoc("logout400", "로그아웃 API") {
          request {
            body {
              "accessToken" type "String" mean "accessToken"
              "refreshToken" type "String" mean "refreshToken"
            }
          }
        }
      }

      scenario("500 INTERNAL_SERVER_ERROR 로그아웃 실패") {
        // given
        val request = LogoutRequest.builder().accessToken("accessToken").refreshToken("refreshToken").build()
        every { loginService.logout(request) } throws RuntimeException("Internal server error")

        // when
        val result =
          webTestClient.post().uri("/api/v1/logout")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()

        // then
        val document =
          result.expectStatus().is5xxServerError
            .expectBody()

        // docs
        document.restDoc("logout500", "로그아웃 API") {
          request {
            body {
              "accessToken" type "String" mean "accessToken"
              "refreshToken" type "String" mean "refreshToken"
            }
          }
        }
      }
    }
  })
