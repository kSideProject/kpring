package kpring.core.global.exception

import com.fasterxml.jackson.databind.ObjectMapper
import kpring.core.global.dto.response.ApiResponse
import org.apache.commons.logging.LogFactory
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpResponse
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.stream.Collectors

/**
rest api 사용시 에러 처리에 대한 로직을 다룹니다.

실패시 api 응답바디는 ApiResponse<Any> 타입으로 다음과 같은 에러 형식을 기대합니다.
```json
{
"message": "실패 사유"
}
```
 */
object HttpClientErrorHandle {
  private val om = ObjectMapper()
  private val log = LogFactory.getLog("REST API ERROR")

  /**
   * 4xx 상태 코드를 응답받을 때, 사용하는 핸들러입니다.
   *
   * 1. request, response 로그를 출력합니다.
   * 2. 응답 받은 상태코드와 에러 메시지를 가공하지 않고 반환합니다.
   */
  fun rest4xxHandle(): (request: HttpRequest, response: ClientHttpResponse) -> Unit = { req, res ->
    val body = BufferedReader(InputStreamReader(res.body, StandardCharsets.UTF_8))
      .lines()
      .collect(Collectors.joining("\n"))

    // logging
    val id = UUID.randomUUID().toString()
    printRequest(id, req)
    printResponse(id, res, body)

    // error handling
    val type = om.typeFactory.constructParametricType(ApiResponse::class.java, Any::class.java)
    val response: ApiResponse<Any> = om.readValue(body, type)
    val status = HttpStatus.valueOf(res.statusCode.value())
    val message = response.message ?: throw ServiceException(CommonErrorCode.INTERNAL_SERVER_ERROR)

    throw ServiceException(DetailErrorCode(message, status))
  }

  /**
   * 5xx 상태 코드를 응답받을 때, 사용하는 핸들러입니다.
   * 내부 서버 오류 발생시 에러를 노출시키지 않기 위해서 로깅만 사용한다.
   *
   * 1. request, response 로그를 출력합니다.
   * 2. 응답 받은 에러 메시지와 상태에 무관하게 내부 서버 오류로 처리합니다.
   */
  fun rest5xxHandle(): (request: HttpRequest, response: ClientHttpResponse) -> Unit = { req, res ->
    val body = BufferedReader(InputStreamReader(res.body, StandardCharsets.UTF_8))
      .lines()
      .collect(Collectors.joining("\n"))

    // logging
    val id = UUID.randomUUID().toString()
    printRequest(id, req)
    printResponse(id, res, body)

    val type = om.typeFactory.constructParametricType(ApiResponse::class.java, Any::class.java)
    val response: ApiResponse<Any> = om.readValue(body, type)
    val status = HttpStatus.valueOf(res.statusCode.value())
    val message = response.message ?: throw ServiceException(CommonErrorCode.INTERNAL_SERVER_ERROR)
    log.error("[$id] request failed with 5xx error: cause=${message}, status=${status}")

    throw ServiceException(CommonErrorCode.INTERNAL_SERVER_ERROR)
  }

  private fun printRequest(
    sessionNumber: String,
    req: HttpRequest,
  ) {
    log.warn("[$sessionNumber] URI: ${req.uri}, Method: ${req.method}, Headers:${req.headers}")
  }

  private fun printResponse(
    sessionNumber: String,
    res: ClientHttpResponse,
    body: String,
  ) {
    log.warn("[$sessionNumber] Status: ${res.statusCode}, Headers:${res.headers}, Body:$body")
  }
}
