package kpring.user.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.junit5.MockKExtension
import kpring.test.restdoc.dsl.restDoc
import kpring.user.dto.request.CreateUserRequest
import kpring.user.dto.result.CreateUserResponse
import kpring.user.dto.result.FailMessageResponse
import kpring.user.exception.ErrorCode
import kpring.user.exception.ExceptionWrapper
import kpring.user.service.UserService
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import org.springframework.web.context.WebApplicationContext

@WebMvcTest(controllers = [UserController::class])
@ExtendWith(value = [MockKExtension::class])
class UserControllerTest(
    val objectMapper: ObjectMapper,
    webContext: WebApplicationContext,
    val restDocument: ManualRestDocumentation = ManualRestDocumentation(),
    @MockkBean val userService: UserService,
) : DescribeSpec(
    {

        val webTestClient = MockMvcWebTestClient.bindToApplicationContext(webContext)
            .configureClient()
            .filter(
                WebTestClientRestDocumentation.documentationConfiguration(restDocument)
                    .operationPreprocessors()
                    .withRequestDefaults(prettyPrint())
                    .withResponseDefaults(prettyPrint())
            )
            .build()

        beforeSpec { restDocument.beforeTest(this.javaClass, "user controller") }

        afterSpec { restDocument.afterTest() }

        describe("회원가입 API") {

            it("회원가입 성공") {
                // given
                val request = CreateUserRequest.builder().email("test@email.com").build()
                val response = CreateUserResponse.builder().build()
                every { userService.createUser(request) } returns response

                // when
                val result = webTestClient.post()
                    .uri("/api/v1/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .exchange()

                // then
                val docsRoot = result
                    .expectStatus().isOk
                    .expectBody()

                // docs
                docsRoot
                    .restDoc("createUser200", "회원가입 API")
                    {
                        request {
                            header {
                                "Content-Type" mean "application/json"
                            }
                            body {
                                "email" type String mean "이메일"
                            }
                        }
                    }
            }

            it("회원가입 실패 : 이미 존재하는 이메일") {
                // given
                val request = CreateUserRequest.builder().email("test@email.com").build()
                val exception = ExceptionWrapper(ErrorCode.ALREADY_EXISTS_EMAIL)
                val response = FailMessageResponse.builder().message(exception.errorCode.message).build()
                every { userService.createUser(request) } throws exception

                // when
                val result = webTestClient.post()
                    .uri("/api/v1/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .exchange()

                // then
                val docsRoot = result
                    .expectStatus().isBadRequest
                    .expectBody().json(
                        objectMapper.writeValueAsString(response)
                    )

                // docs
                docsRoot
                    .restDoc(
                        identifier = "createUser400",
                        description = "회원가입 API"
                    )
                    {
                        request {
                            header {
                                "Content-Type" mean "application/json"
                            }
                            body {
                                "email" type String mean "이메일"
                            }
                        }

                        response {
                            body {
                                "message" type String mean "에러 메시지"
                            }
                        }
                    }
            }

            it("회원가입 실패 : 필수입력 값 미전송") {
                // given
                val request = CreateUserRequest.builder().build()
                val responseMessage = "필수 입력값이 누락되었습니다."
                val response = FailMessageResponse.builder().message(responseMessage).build()

                // when
                val result = webTestClient.post()
                    .uri("/api/v1/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .exchange()

                // then
                val docsRoot = result
                    .expectStatus().isBadRequest
                    .expectBody().json(
                        objectMapper.writeValueAsString(response)
                    )

                // docs
                docsRoot
                    .restDoc(
                        identifier = "createUser400",
                        description = "회원가입 API"
                    )
                    {
                        request {
                            header {
                                "Content-Type" mean "application/json"
                            }
                            body {
                                "email" type String mean "이메일"
                            }
                        }

                        response {
                            body {
                                "message" type String mean "에러 메시지"
                            }
                        }
                    }
            }

            it("회원가입 실패 : 서버 내부 오류") {
                // given
                val request = CreateUserRequest.builder().email("test@email.com").build()
                val exception = RuntimeException("서버 내부 오류")
                val response = FailMessageResponse.builder().message("서버 오류").build()
                every { userService.createUser(request) } throws exception

                // when
                val result = webTestClient.post()
                    .uri("/api/v1/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .exchange()

                // then
                val docsRoot = result
                    .expectStatus().is5xxServerError
                    .expectBody().json(
                        objectMapper.writeValueAsString(response)
                    )

                // docs
                docsRoot
                    .restDoc(
                        identifier = "createUser500",
                        description = "회원가입 API"
                    )
                    {
                        request {
                            header {
                                "Content-Type" mean "application/json"
                            }
                            body {
                                "email" type String mean "이메일"
                            }
                        }

                        response {
                            body {
                                "message" type String mean "에러 메시지"
                            }
                        }
                    }
            }

        }
    }
) {


//    @Test
//    @WithMockUser(username = "testUser", roles = ["USER"])
//    fun `친구추가 성공케이스`() {
//        val userId = 1L;
//        val friendsRequestDto = AddFriendRequest(friendId = 2L)
//
//        webTestClient.post()
//            .uri("/api/v1/user/{userId}/friend/{friendId}", userId, 2)
//            .contentType(MediaType.APPLICATION_JSON)
//            .bodyValue(friendsRequestDto)
//            .exchange()
//            .expectStatus().isOk
//            .expectBody()
//    }
//
//    @Test
//    @WithMockUser(username = "testUser", roles = ["USER"])
//    fun `친구추가_실패케이스`() {
//        val userId = -1L // 유효하지 않은 사용자 아이디
//        val friendsRequestDto = AddFriendRequest(friendId = 2L)
//
//        webTestClient.post()
//            .uri("/api/v1/user/{userId}/friend/{friendId}", userId, 2)
//            .contentType(MediaType.APPLICATION_JSON)
//            .bodyValue(friendsRequestDto)
//            .exchange()
//            .expectStatus().isBadRequest
//            .expectBody()
//    }
}
