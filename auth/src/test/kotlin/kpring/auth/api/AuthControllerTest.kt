package kpring.auth.api

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.junit5.MockKExtension
import kpring.auth.api.v1.AuthController
import kpring.auth.service.TokenService
import kpring.core.auth.dto.request.CreateTokenRequest
import kpring.core.auth.dto.response.CreateTokenResponse
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.headers.HeaderDocumentation.*
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
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

                every { tokenService.createToken(any()) } returns CreateTokenResponse(
                    accessToken = "Bearer access token",
                    accessExpireAt = LocalDateTime.of(2000, 1, 1, 0, 0, 0),
                    refreshToken = "Bearer refresh token",
                    refreshExpireAt = LocalDateTime.of(2000, 1, 1, 0, 1, 0),
                )

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
                    .consumeWith(
                        document(
                            "post_api.v1.token",
                            requestHeaders(
                                headerWithName("Content-Type").description("application/json"),
                            ),
                            requestFields(
                                fieldWithPath("id").description("유저 식별 아이디").type(String),
                                fieldWithPath("nickname").description("닉네임").type(String)
                            ),

                            responseHeaders(
                                headerWithName("Authorization").description("jwt access token 정보"),
                                headerWithName("Content-Type").description("application/json"),
                            ),
                            responseFields(
                                fieldWithPath("accessToken").description("jwt access token").type(String),
                                fieldWithPath("accessExpireAt").description("jwt access token 만료시간")
                                    .type("yyyy-MM-dd hh:mm:ss"),
                                fieldWithPath("refreshToken").description("jwt refresh token").type(String),
                                fieldWithPath("refreshExpireAt").description("jwt refresh token 만료시간")
                                    .type("yyyy-MM-dd hh:mm:ss"),
                            ),
                        ),
                    )
            }

            When("GET") {
                webTestClient.get().uri(url)
            }

            When("DELETE") {
                webTestClient.delete().uri(url)
            }
        }

        Given("/api/v1/validation") {
            val url = "/api/v1/validation"
        }

    },
)
