package kpring.core.global.exception

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kpring.core.global.dto.response.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.mock.http.client.MockClientHttpRequest
import org.springframework.mock.http.client.MockClientHttpResponse

class HttpClientErrorHandleTest : DescribeSpec({

  val om = ObjectMapper()

  it("4xx 에러 핸들러 테스트 : 응답 받은 상태코드와 메시지를 반환하는 에러를 던진다.") {
    // given
    val request = MockClientHttpRequest()
    val responseData = ApiResponse<Any>(message = "test error")
    val response = MockClientHttpResponse(
      om.writeValueAsBytes(responseData), HttpStatusCode.valueOf(400)
    )
    val handler = HttpClientErrorHandle.rest4xxHandle()
    // when
    val ex = shouldThrow<ServiceException> {
      handler(request, response)
    }

    // then
    ex.errorCode.message() shouldBe "test error"
    ex.errorCode.httpStatus() shouldBe HttpStatus.BAD_REQUEST
  }

  it("5xx 에러 핸들러 테스트 : 응답과 무관하게 내부 서버 오류 처리합니다.") {
    // given
    val request = MockClientHttpRequest()
    val responseData = ApiResponse<Any>(message = "origin server error message")
    val response = MockClientHttpResponse(
      om.writeValueAsBytes(responseData), HttpStatusCode.valueOf(503)
    )
    val handler = HttpClientErrorHandle.rest5xxHandle()
    // when
    val ex = shouldThrow<ServiceException> {
      handler(request, response)
    }

    // then
    ex.errorCode shouldBe CommonErrorCode.INTERNAL_SERVER_ERROR
  }
})
