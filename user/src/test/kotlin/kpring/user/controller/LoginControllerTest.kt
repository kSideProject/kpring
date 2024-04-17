package kpring.user.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.FeatureSpec
import io.mockk.every
import io.mockk.junit5.MockKExtension
import kpring.user.dto.request.LoginRequest
import kpring.user.dto.request.LogoutRequest
import kpring.user.dto.result.LoginResponse
import kpring.user.service.LoginService
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(controllers = [LoginController::class])
@ExtendWith(value = [MockKExtension::class])
@AutoConfigureMockMvc
class LoginControllerTest(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
    @MockkBean val loginService: LoginService,
) : FeatureSpec({

    feature("API : login API") {
        scenario("200 OK 로그인 성공") {
            // given
            val request = LoginRequest.builder().email("test@email.com").build()
            val response = LoginResponse.builder().accessToken("accessToken").refreshToken("refreshToken").build()
            every { loginService.login(request) } returns response

            // when
            val result = mockMvc.post("/api/v1/login") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(request)
            }.andDo { print() }

            // then
            result.andExpect {
                status { isOk() }
                header { string("Authorization", "accessToken") }
                content { json(objectMapper.writeValueAsString(response)) }
            }
        }

        scenario("400 BAD_REQUEST 로그인 실패") {
            // given
            val request = LoginRequest.builder().email("test@gmail.com").build()
            every { loginService.login(request) } throws IllegalArgumentException("Invalid email")

            // when
            val result = mockMvc.post("/api/v1/login") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(request)
            }.andDo { print() }

            // then
            result.andExpect {
                status { isBadRequest() }
            }
        }

        scenario("500 INTERNAL_SERVER_ERROR 로그인 실패") {
            // given
            val request = LoginRequest.builder().email("test@naver.com").build()
            every { loginService.login(request) } throws RuntimeException("Internal server error")

            // when
            val result = mockMvc.post("/api/v1/login") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(request)
            }.andDo { print() }

            // then
            result.andExpect {
                status { isInternalServerError() }
            }
        }
    }

    feature("API : logout API") {
        scenario("200 OK 로그아웃 성공") {
            // given
            val request = LogoutRequest.builder().accessToken("accessToken").refreshToken("refreshToken").build()
            every { loginService.logout(request) } returns Unit

            // when
            val result = mockMvc.post("/api/v1/logout") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(request)
            }.andDo { print() }

            // then
            result.andExpect {
                status { isOk() }
            }
        }

        scenario("400 BAD_REQUEST 로그아웃 실패") {
            // given
            val request = LogoutRequest.builder().accessToken("accessToken").refreshToken("refreshToken").build()
            every { loginService.logout(request) } throws IllegalArgumentException("Invalid token")

            // when
            val result = mockMvc.post("/api/v1/logout") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(request)
            }.andDo { print() }

            // then
            result.andExpect {
                status { isBadRequest() }
            }
        }

        scenario("500 INTERNAL_SERVER_ERROR 로그아웃 실패") {
            // given
            val request = LogoutRequest.builder().accessToken("accessToken").refreshToken("refreshToken").build()
            every { loginService.logout(request) } throws RuntimeException("Internal server error")

            // when
            val result = mockMvc.post("/api/v1/logout") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(request)
            }.andDo { print() }

            // then
            result.andExpect {
                status { isInternalServerError() }
            }
        }
    }
})
