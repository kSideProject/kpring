package kpring.auth.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.print.print
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coJustRun
import io.mockk.every
import io.mockk.junit5.MockKExtension
import kpring.auth.api.v1.AuthController
import kpring.auth.service.TokenService
import kpring.core.auth.dto.request.CreateTokenRequest
import kpring.core.auth.dto.response.CreateTokenResponse
import kpring.core.auth.dto.response.ReCreateAccessTokenResponse
import kpring.test.restdoc.dsl.restDoc
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import java.time.LocalDateTime

@WebFluxTest(controllers = [AuthController::class])
@AutoConfigureRestDocs
@ExtendWith(
    value = [
        MockKExtension::class,
    ]
)
class AuthControllerTest(
    private val applicationContext: ApplicationContext,
    private val objectMapper: ObjectMapper,
    @MockkBean val tokenService: TokenService,
) : BehaviorSpec(
    {
        val restDocumentation = ManualRestDocumentation()
        val webTestClient: WebTestClient = WebTestClient.bindToApplicationContext(applicationContext)
            .configureClient()
            .filter(
                documentationConfiguration(restDocumentation)
                    .operationPreprocessors()
                    .withRequestDefaults(prettyPrint())
                    .withResponseDefaults(prettyPrint())
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

                val response = CreateTokenResponse(
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
                    .bodyValue(
                        CreateTokenRequest(
                            id = "test",
                            nickname = "mock user",
                        ),
                    )
                    .exchange()
                    .expectStatus().isOk
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectHeader().valuesMatch("Authorization", "Bearer .+")
                    .expectBody()
                    .json(objectMapper.writeValueAsString(response))
                    .restDoc("post_api.v1.token") {
                        request {
                            header {
                                "Content-Type" mean "application/json"
                            }

                            body {
                                "id" type "String" mean "유저 식별 아이디"
                                "nickname" type "String" mean "닉네임"
                            }
                        }

                        response {
                            header {
                                "Authorization" mean "jwt access token 정보"
                                "Content-Type" mean "application/json"
                            }

                            body {
                                "accessToken" type "String" mean "jwt access token"
                                "accessExpireAt" type "yyyy-MM-dd hh:mm:ss" mean "jwt access token 만료시간"
                                "refreshToken" type "String" mean "jwt refresh token"
                                "refreshExpireAt" type "yyyy-MM-dd hh:mm:ss" mean "jwt refresh token 만료시간"
                            }
                        }
                    }
            }

            When("GET") {

                val response = ReCreateAccessTokenResponse(
                    accessToken = "testToken",
                    accessExpireAt = LocalDateTime.of(1900, 1, 1, 0, 0, 0)
                )
                every { tokenService.createAccessToken(any()) } returns response

                webTestClient.get().uri(url)
                    .header("Authorization", "test token")
                    .exchange()
                    .expectStatus().isOk
                    .expectBody()
                    .json(objectMapper.writeValueAsString(response))
                    .restDoc("get_api.v1.token") {
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
                                "accessToken" type "String" mean "jwt access token"
                                "accessExpireAt" type "yyyy-MM-dd hh:mm:ss" mean "jwt access token 만료시간"
                            }
                        }
                    }
            }

            When("DELETE") {

                coJustRun { tokenService.expireToken(any()) }
                webTestClient.delete().uri("$url/{}")
                    .exchange()
                    .expectStatus().isOk
                    .expectBody().apply {
                        isEmpty()
                        restDoc("delete_api.v1.token") { }
                    }
            }
        }

        Given("/api/v1/validation") {
            val url = "/api/v1/validation"
        }

    },
)
