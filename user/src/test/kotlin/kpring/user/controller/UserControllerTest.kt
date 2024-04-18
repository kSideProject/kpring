package kpring.user.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.AnnotationSpec
import kpring.user.dto.request.AddFriendRequest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.event.annotation.AfterTestMethod
import org.springframework.test.context.event.annotation.BeforeTestMethod
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest(
    webContext: WebApplicationContext,
    val restDocument: ManualRestDocumentation = ManualRestDocumentation(),
) : AnnotationSpec() {

    private val webTestClient = WebTestClient.bindToApplicationContext(webContext)
        .configureClient()
        .filter(
            WebTestClientRestDocumentation.documentationConfiguration(restDocument)
                .operationPreprocessors()
                .withRequestDefaults(prettyPrint())
                .withResponseDefaults(prettyPrint())
        )
        .build()

    @BeforeTestMethod
    fun beforeTest() {
        restDocument.beforeTest(this.javaClass, "user controller")
    }

    @AfterTestMethod
    fun afterTest() {
        restDocument.afterTest()
    }

    @Test
    @WithMockUser(username = "testUser", roles = ["USER"])
    fun `친구추가 성공케이스`() {
        val userId = 1L;
        val friendsRequestDto = AddFriendRequest(friendId = 2L)

        webTestClient.post()
            .uri("/api/v1/user/{userId}/friend/{friendId}", userId, 2)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(friendsRequestDto)
            .exchange()
            .expectStatus().isOk
            .expectBody()
    }

    @Test
    @WithMockUser(username = "testUser", roles = ["USER"])
    fun `친구추가_실패케이스`() {
        val userId = -1L // 유효하지 않은 사용자 아이디
        val friendsRequestDto = AddFriendRequest(friendId = 2L)

        webTestClient.post()
            .uri("/api/v1/user/{userId}/friend/{friendId}", userId, 2)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(friendsRequestDto)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
    }
}
