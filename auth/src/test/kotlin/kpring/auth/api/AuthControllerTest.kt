package kpring.auth.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.jsonwebtoken.ExpiredJwtException
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kpring.auth.api.v1.AuthController
import kpring.auth.service.TokenService
import kpring.core.auth.dto.request.CreateTokenRequest
import kpring.core.auth.dto.response.CreateTokenResponse
import kpring.core.auth.dto.response.ReCreateAccessTokenResponse
import kpring.core.auth.dto.response.TokenInfo
import kpring.core.auth.enums.TokenType
import kpring.core.global.dto.response.ApiResponse
import kpring.test.restdoc.dsl.restDoc
import kpring.test.restdoc.json.JsonDataType.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDateTime

@WebFluxTest(
  controllers = [
    AuthController::class,
    kpring.auth.api.v2.AuthController::class,
  ],
)
@AutoConfigureRestDocs
@ExtendWith(
  value = [
    MockKExtension::class,
  ],
)
class AuthControllerTest(
  private val applicationContext: ApplicationContext,
  private val objectMapper: ObjectMapper,
  @MockkBean val tokenService: TokenService,
) : BehaviorSpec(
    {
      val restDocumentation = ManualRestDocumentation()
      val webTestClient: WebTestClient =
        WebTestClient.bindToApplicationContext(applicationContext)
          .configureClient()
          .filter(
            documentationConfiguration(restDocumentation)
              .operationPreprocessors()
              .withRequestDefaults(prettyPrint())
              .withResponseDefaults(prettyPrint()),
          )
          .build()

      beforeSpec { restDocumentation.beforeTest(javaClass, "auth controller") }
      afterSpec { restDocumentation.afterTest() }

    /*
    test code
     */
      Given("/api/v1/token") {
        val url = "/api/v1/token"
        When("POST") {

          then("200 OK") {
            val request =
              CreateTokenRequest(
                id = "test",
                nickname = "mock user",
              )
            val response =
              CreateTokenResponse(
                accessToken = "Bearer access token",
                accessExpireAt = LocalDateTime.of(2000, 1, 1, 0, 0, 0),
                refreshToken = "Bearer refresh token",
                refreshExpireAt = LocalDateTime.of(2000, 1, 1, 0, 1, 0),
              )

            every { tokenService.createToken(any()) } returns response

            webTestClient
              .post()
              .uri(url)
              .contentType(MediaType.APPLICATION_JSON)
              .bodyValue(request)
              .exchange()
              .expectStatus().isOk
              .expectHeader().contentType(MediaType.APPLICATION_JSON)
              .expectHeader().valuesMatch("Authorization", "Bearer .+")
              .expectBody()
              .json(objectMapper.writeValueAsString(response))
              .restDoc("post_api.v1.token", "토큰 생성") {
                request {
                  header {
                    "Content-Type" mean "application/json"
                  }

                  body {
                    "id" type Strings mean "유저 식별 아이디"
                    "nickname" type Strings mean "닉네임"
                  }
                }

                response {
                  header {
                    "Authorization" mean "jwt access token 정보"
                    "Content-Type" mean "application/json"
                  }

                  body {
                    "accessToken" type Strings mean "jwt access token"
                    "accessExpireAt" type Strings mean "jwt access token 만료시간입니다. 형식은 yyyy-MM-dd hh:mm:ss을 제공합니다."
                    "refreshToken" type Strings mean "jwt refresh token"
                    "refreshExpireAt" type Strings mean "jwt refresh token 만료시간 형식은 yyyy-MM-dd hh:mm:ss을 제공합니다."
                  }
                }
              }
          }

          then("400 BAD REQUEST") {
            webTestClient.post().uri(url)
              .contentType(MediaType.APPLICATION_JSON)
              .bodyValue(
                CreateTokenRequest(
                  // request must contains id and nickname
                  id = "",
                  nickname = "",
                ),
              )
              .exchange()
              .expectStatus().isBadRequest
              .expectBody()
              .restDoc(
                identifier = "post_api.v1.token_400",
                description = "요청자의 request에 정보가 부족한 경우 토큰을 생성할 수 없습니다.",
              ) { }
          }

          then("500 INTERNAL SERVER ERROR") {

            val request =
              CreateTokenRequest(
                id = "test",
                nickname = "mock user",
              )

            every { tokenService.createToken(any()) } throws NullPointerException("서버에서 오류가 발생하는 경우")
            webTestClient.post().uri(url)
              .bodyValue(request)
              .exchange()
              .expectStatus().isEqualTo(500)
              .expectBody()
              .restDoc(
                identifier = "post_api.v1.token_500",
                description = "서버의 이상으로 인해서 토큰이 생성이 실패한 경우입니다.",
              ) { }
          }
        }

        When("DELETE") {

          then("200 OK") {
            coJustRun { tokenService.expireToken(any()) }
            webTestClient.delete()
              .uri(
                "$url/{jwtToken}",
                mutableMapOf(
                  "jwtToken" to "tokenValue",
                ),
              )
              .exchange()
              .expectStatus().isOk
              .expectBody().apply {
                isEmpty()
                restDoc("delete_api.v1.token", "토큰 만료(삭제)") { }
              }
          }

          then("400 BAD REQUEST") {
            coEvery { tokenService.expireToken(any()) } throws IllegalArgumentException()

            webTestClient.delete()
              .uri(
                "$url/{jwtToken}",
                mutableMapOf(
                  "jwtToken" to "tokenValue",
                ),
              )
              .exchange()
              .expectStatus().isBadRequest
              .expectBody().apply {
                isEmpty()
                restDoc("delete_api.v1.token_400", "토큰 만료(삭제)") { }
              }
          }

          then("500 INTERNAL SERVER ERROR") {
            coEvery { tokenService.expireToken(any()) } throws NullPointerException()
            webTestClient.delete()
              .uri(
                "$url/{jwtToken}",
                mutableMapOf(
                  "jwtToken" to "tokenValue",
                ),
              )
              .exchange()
              .expectStatus().isEqualTo(500)
              .expectBody().apply {
                isEmpty()
                restDoc("delete_api.v1.token_500", "토큰 만료(삭제)") { }
              }
          }
        }
      }

      Given("/api/v1/access_token") {
        val url = "/api/v1/access_token"

        When("POST") {
          val response =
            ReCreateAccessTokenResponse(
              accessToken = "testToken",
              accessExpireAt = LocalDateTime.of(1900, 1, 1, 0, 0, 0),
            )
          then("200 OK") {
            coEvery { tokenService.reCreateAccessToken(any()) } returns response

            webTestClient.post().uri(url)
              .header("Authorization", "test token")
              .exchange()
              .expectStatus().isOk
              .expectBody()
              .json(objectMapper.writeValueAsString(response))
              .restDoc("post.v1.token", "토큰 재발급(갱신)") {
                request {
                  header {
                    "Authorization" mean "jwt access token 정보"
                  }
                }

                response {
                  header {
                    "Authorization" mean "jwt access token 정보"
                  }

                  body {
                    "accessToken" type Strings mean "jwt access token"
                    "accessExpireAt" type Strings mean "jwt access token 만료시간 형식은 yyyy-MM-dd hh:mm:ss을 제공합니다."
                  }
                }
              }
          }

          then("400 BAD REQUEST") {

            coEvery { tokenService.reCreateAccessToken(any()) } returns response

            webTestClient.post().uri(url)
              .exchange()
              .expectStatus().isBadRequest
              .expectBody()
              .restDoc(
                identifier = "post.v1.token_400",
                description = "필요한 파라미터를 모두 입력하지 않은 사용자 실수인 경우",
              ) { }
          }

          then("403 FORBIDDEN") {

            coEvery { tokenService.reCreateAccessToken(any()) } throws
              ExpiredJwtException(
                mockk(),
                mockk(),
                "",
              )

            webTestClient.post().uri(url)
              .exchange()
              .expectStatus().isBadRequest
              .expectBody()
              .restDoc(
                identifier = "post.v1.token_403",
                description = "유효하지 않은 토큰인 경우",
              ) { }
          }

          then("500 INTERNAL SERVER ERROR") {

            coEvery { tokenService.reCreateAccessToken(any()) } throws NullPointerException("서버 오류")

            webTestClient.post().uri(url)
              .header("Authorization", "test token")
              .exchange()
              .expectStatus().isEqualTo(500)
              .expectBody()
              .restDoc(
                identifier = "post.v1.token_500",
                description = "서버 내부 오류시",
              ) { }
          }
        }
      }

      Given("/api/v2/validation") {
        val url = "/api/v2/validation"
        val testToken = "Bearer testtoken"

        coEvery { tokenService.checkToken(any()) } returns TokenInfo(TokenType.ACCESS, "testUserId")

        When("POST") {

          val data = TokenInfo(TokenType.ACCESS, "testUserId")
          then("200 OK") {
            webTestClient.post().uri(url)
              .header("Authorization", testToken)
              .exchange()
              .expectStatus().isOk
              .expectBody()
              .json(
                objectMapper.writeValueAsString(
                  ApiResponse(data = data),
                ),
              )
              .restDoc("post.v2.validation200", "토큰 검증") {
                request {
                  header { "Authorization" mean "검증할 토큰 정보" }
                }

                response {
                  header { "Content-type" mean "entity type" }
                  body {
                    "data.type" type Strings mean "ACCESS  또는 REFRESH 값을 가지며 검증한 토큰의 타입입니다. 만약 유효하지 않은 토큰이라면 존재하지 않습니다."
                    "data.userId" type Strings mean "토큰에 저장된 유저의 id"
                  }
                }
              }
          }

          then("400 BAD REQUEST") {
            val token = ""
            coEvery { tokenService.checkToken(token) } returns data
            webTestClient.post()
              .uri(url)
              .exchange()
              .expectStatus().isBadRequest
              .expectBody()
              .restDoc(
                identifier = "post.v2.validation400",
                description = "필요한 파라미터를 모두 입력하지 않은 사용자 실수인 경우",
              ) {}
          }

          then("500 INTERNAL SERVER ERROR") {
            val token = "validateToken500"
            coEvery { tokenService.checkToken(token) } throws NullPointerException("server error")
            webTestClient.post()
              .uri(url)
              .header("Authorization", token)
              .exchange()
              .expectStatus().isEqualTo(500)
              .expectBody()
              .restDoc(
                identifier = "post.v2.validation500",
                description = "서버 내부 오류시",
              ) { }
          }
        }
      }
    },
  )
