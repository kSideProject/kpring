package kpring.chat.chat.api.v1

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.junit5.MockKExtension
import kpring.chat.chat.dto.ChatInfo
import kpring.chat.chat.service.ChatService
import kpring.core.auth.client.AuthClient
import kpring.core.auth.dto.response.TokenValidationResponse
import kpring.core.auth.enums.TokenType
import kpring.test.restdoc.dsl.restDoc
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.ResponseEntity
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import org.springframework.web.context.WebApplicationContext

@WebMvcTest(controllers = [ChatController::class])
@ExtendWith(value = [MockKExtension::class])
class ChatControllerTest(
    @MockkBean private val authClient: AuthClient,
    @MockkBean private val chatService: ChatService,
    val context: WebApplicationContext,
) : DescribeSpec({

    val restDocumentation = ManualRestDocumentation()
    val webTestClient: WebTestClient = MockMvcWebTestClient.bindToApplicationContext(context)
        .configureClient()
        .filter(
            documentationConfiguration(restDocumentation)
                .operationPreprocessors()
                .withRequestDefaults(prettyPrint())
                .withResponseDefaults(prettyPrint())
        )
        .build()

    beforeSpec { restDocumentation.beforeTest(javaClass, "login controller") }
    afterSpec { restDocumentation.afterTest() }

    context("채팅 조회 API") {
        it("성공시") {
            // given
            val userId = "testUserId"
            every { authClient.validateToken(any()) } returns ResponseEntity.ok()
                .body(TokenValidationResponse(true, TokenType.ACCESS, userId))
            every { chatService.getChatList(any(), any()) } returns listOf(
                ChatInfo(
                    "chatId",
                    "testUserId",
                    "roomId",
                    "nickname",
                    "content"
                )
            )

            // when
            val result = webTestClient
                .get().uri("/api/v1/chat?size=10&page=0")
                .header("Authorization", "mocking")
                .exchange()

            // then
            val document = result.expectStatus().isOk
                .expectBody()

            // docs
            document.restDoc("chat200", "채팅 조회 API") {
                request {
                    header { "Authorization" mean "토큰" }
                    query {
                        "size" mean "페이지 사이즈"
                        "page" mean "페이지 번호"
                    }
                }
                response {
                    body {
                        "message" type "String" mean "응답 관련 설명 메시지"
                        "data" type "Array" mean "채팅 리스트"
                        "data[*].chatId" type "String" mean "채팅 아이디"
                        "data[*].userId" type "String" mean "유저 아이디"
                        "data[*].roomId" type "String" mean "room 아이디"
                        "data[*].nickname" type "String" mean "닉네임"
                        "data[*].content" type "String" mean "채팅 내용"
                    }
                }
            }
        }
    }
})
