package kpring.server.adapter.input.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import kpring.core.auth.client.AuthClient
import kpring.core.auth.dto.response.TokenInfo
import kpring.core.auth.enums.TokenType
import kpring.core.global.dto.response.ApiResponse
import kpring.core.global.exception.CommonErrorCode
import kpring.core.global.exception.ServiceException
import kpring.core.server.dto.ServerInfo
import kpring.core.server.dto.ServerSimpleInfo
import kpring.core.server.dto.request.AddUserAtServerRequest
import kpring.core.server.dto.request.CreateServerRequest
import kpring.core.server.dto.request.GetServerCondition
import kpring.core.server.dto.response.CreateServerResponse
import kpring.server.application.service.CategoryService
import kpring.server.application.service.ServerService
import kpring.server.config.CoreConfiguration
import kpring.server.domain.Category
import kpring.server.domain.Theme
import kpring.server.util.toInfo
import kpring.test.restdoc.dsl.restDoc
import kpring.test.restdoc.json.JsonDataType.*
import kpring.test.web.MvcWebTestClientDescribeSpec
import kpring.test.web.URLBuilder
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
class RestApiServerControllerTest(
  private val om: ObjectMapper,
  webContext: WebApplicationContext,
  @MockkBean val serverService: ServerService,
  @MockkBean val authClient: AuthClient,
  @MockkBean val categoryService: CategoryService,
) : MvcWebTestClientDescribeSpec(
    testMethodName = "RestApiServerControllerTest",
    webContext = webContext,
    body = { client ->

      afterTest { clearMocks(authClient) }

      describe("POST /api/v1/server : createServer api test") {
        val url = "/api/v1/server"
        it("요청 성공시") {
          // given
          val userId = "test_user_id"

          val request = CreateServerRequest(serverName = "test server", userId = userId)
          val data =
            CreateServerResponse(
              serverId = "1",
              serverName = request.serverName,
              theme = Theme.default().toInfo(),
              categories = listOf(Category.SERVER_CATEGORY1, Category.SERVER_CATEGORY2).map(Category::toInfo),
            )

          every { authClient.getTokenInfo(any()) } returns
            ApiResponse(
              data =
                TokenInfo(
                  type = TokenType.ACCESS,
                  userId = userId,
                ),
            )
          every { serverService.createServer(eq(request)) } returns data

          // when
          val result =
            client.post()
              .uri(url)
              .header("Authorization", "Bearer mock_token")
              .bodyValue(request)
              .exchange()

          // then
          val docs =
            result
              .expectStatus().isOk
              .expectBody()
              .json(om.writeValueAsString(ApiResponse(data = data)))

          // docs
          docs.restDoc(
            identifier = "create_server_200",
            description = "서버 생성 api",
          ) {
            request {
              header { "Authorization" mean "jwt access token" }
              body {
                "serverName" type Strings mean "생성할 서버의 이름"
                "userId" type Strings mean "서버를 생성하는 유저의 id"
                "theme" type Strings mean "생성할 서버의 테마" optional true
                "categories" type Arrays mean "생성할 서버의 카테고리 목록" optional true
              }
            }

            response {
              body {
                "data.serverId" type Strings mean "서버 id"
                "data.serverName" type Strings mean "생성된 서버 이름"

                "data.theme.id" type Strings mean "테마 id"
                "data.theme.name" type Strings mean "테마 이름"

                "data.categories[].id" type Strings mean "카테고리 id"
                "data.categories[].name" type Strings mean "카테고리 이름"
              }
            }
          }
        }

        it("요청 실패시 : 요청한 유저와 서버 권한을 가진 유저가 일치하지 않는 경우") {
          // given
          val serverOwnerId = "server owner id"

          val request = CreateServerRequest(serverName = "test server", userId = serverOwnerId)

          every { authClient.getTokenInfo(any()) } returns
            ApiResponse(
              data =
                TokenInfo(
                  type = TokenType.ACCESS,
                  userId = "request user id",
                ),
            )

          // when
          val result =
            client.post()
              .uri(url)
              .header("Authorization", "Bearer mock_token")
              .bodyValue(request)
              .exchange()

          // then
          val docs =
            result
              .expectStatus().isBadRequest
              .expectBody()
              .json(om.writeValueAsString(ApiResponse<Any>(message = "유저 정보가 일치하지 않습니다")))

          // docs
          docs.restDoc(
            identifier = "create_server_fail-400",
            description = "서버 생성 api",
          ) {
            request {
              header { "Authorization" mean "jwt access token" }
              body {
                "serverName" type Strings mean "생성할 서버의 이름"
                "userId" type Strings mean "서버를 생성하는 유저의 id"
                "theme" type Strings mean "생성할 서버의 테마" optional true
                "categories" type Arrays mean "생성할 서버의 카테고리 목록" optional true
              }
            }

            response {
              body {
                "message" type Strings mean "실패 메시지"
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
          val result =
            client.get()
              .uri(url, serverId)
              .exchange()

          // then
          val docs =
            result
              .expectStatus().isOk
              .expectBody()
              .json(om.writeValueAsString(ApiResponse(data = data)))

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
                "data.id" type Strings mean "서버 id"
                "data.name" type Strings mean "생성된 서버 이름"
                "data.users" type Arrays mean "서버에 가입된 유저 목록"
              }
            }
          }
        }

        it("요청 실패 : 존재하지 않은 서버") {
          // given
          val serverId = "not_exist_server_id"
          val errorCode = CommonErrorCode.NOT_FOUND
          every { serverService.getServerInfo(serverId) } throws ServiceException(errorCode)

          // when
          val result =
            client.get()
              .uri(url, serverId)
              .exchange()

          // then
          val docs =
            result
              .expectStatus().isNotFound
              .expectBody()
              .json(om.writeValueAsString(ApiResponse<Any>(message = errorCode.message())))

          // docs
          docs.restDoc(
            identifier = "get_server_info_with_invalid_id",
            description = "서버 단건 조회 api",
          ) {
            request {
              path { "serverId" mean "서버 id" }
            }

            response {
              body {
                "message" type Strings mean "실패 관련 메시지"
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
          val result =
            client.put()
              .uri(url, "test_server_id")
              .header("Authorization", "Bearer mock_token")
              .bodyValue(request)
              .exchange()

          // then
          val docs =
            result
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
                "userId" type Strings mean "가입할 유저 id"
                "userName" type Strings mean "가입할 유저 이름"
                "profileImage" type Strings mean "가입할 유저 프로필 이미지"
              }
            }
          }
        }
      }

      describe("PUT /api/v1/server/{serverId}/invitation/{userId} : 서버 초대 api test") {
        val url = "/api/v1/server/{serverId}/invitation/{userId}"
        it("요청 성공시") {
          // given
          every { authClient.getTokenInfo(any()) } returns
            ApiResponse(
              data =
                TokenInfo(
                  type = TokenType.ACCESS,
                  userId = "test_user_id",
                ),
            )
          justRun { serverService.inviteUser(eq("test_server_id"), any(), any()) }

          // when
          val result =
            client.put()
              .uri(url, "test_server_id", "userId")
              .header("Authorization", "Bearer mock_token")
              .exchange()

          // then
          val docs =
            result
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

      describe("GET /api/v1/server: 서버 목록 조회 api test") {

        val url = "/api/v1/server"
        it("요청 성공시") {
          // given
          val userId = "test user id"
          val data =
            listOf(
              ServerSimpleInfo(id = "server1", name = "test_server", bookmarked = false),
              ServerSimpleInfo(id = "server2", name = "test_server", bookmarked = true),
            )
          val condition = GetServerCondition(serverIds = listOf("server1", "server2"))

          every { authClient.getTokenInfo(any()) } returns
            ApiResponse(
              data = TokenInfo(TokenType.ACCESS, userId),
            )
          every { serverService.getServerList(any(), eq(userId)) } returns data

          // when
          val result =
            client.get()
              .uri(
                URLBuilder(url)
                  .query("serverIds", condition.serverIds!!)
                  .build(),
              )
              .header("Authorization", "Bearer test_token")
              .exchange()

          // then
          val docs =
            result
              .expectStatus().isOk
              .expectBody()
              .json(om.writeValueAsString(ApiResponse(data = data)))

          // docs
          docs.restDoc(
            identifier = "get_server_list_info_200",
            description = "서버 목록 조회 api",
          ) {
            request {
              query { "serverIds" mean "조회시 해당 서버 목록만을 조회합니다. 값이 없다면 조건은 적용되지 않습니다." }
              header { "Authorization" mean "jwt access token" }
            }

            response {
              body {
                "data[].id" type Strings mean "서버 id"
                "data[].name" type Strings mean "서버 이름"
                "data[].bookmarked" type Booleans mean "북마크 여부"
              }
            }
          }
        }
      }

      describe("DELETE /api/v1/server/{serverId} : 서버 삭제") {
        val url = "/api/v1/server/{serverId}"
        it("요청 성공시") {
          // given
          val serverId = "test_server_id"
          val token = "Bearer mock_token"
          every { authClient.getTokenInfo(token) } returns
            ApiResponse(
              data =
                TokenInfo(
                  type = TokenType.ACCESS,
                  userId = "test_user_id",
                ),
            )
          justRun { serverService.deleteServer(eq(serverId), any()) }

          // when
          val result =
            client.delete()
              .uri(url, serverId)
              .header("Authorization", token)
              .exchange()

          // then
          val docs =
            result
              .expectStatus().isOk
              .expectBody()

          // docs
          docs.restDoc(
            identifier = "delete_server_200",
            description = "서버 삭제 api",
          ) {
            request {
              header { "Authorization" mean "jwt 사용자 토큰" }
              path { "serverId" mean "서버 id" }
            }
          }
        }
      }
    },
  )
