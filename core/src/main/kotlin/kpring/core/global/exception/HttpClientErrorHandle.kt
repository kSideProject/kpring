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

  private val objectMapper = ObjectMapper()
  private val log = LogFactory.getLog("REST API ERROR")
  fun rest4xxHandle(): (request: HttpRequest, response: ClientHttpResponse) -> Unit = { req, res ->
    // logging
    val id = UUID.randomUUID().toString()
    printRequest(id, req)
    printResponse(id, res)

    // error handling
    val type =
      objectMapper.typeFactory.constructParametricType(ApiResponse::class.java, Any::class.java)
    val response: ApiResponse<Any> = objectMapper.readValue(res.body, type)
    val status = HttpStatus.valueOf(res.statusCode.value())
    val message = response.message ?: throw ServiceException(CommonErrorCode.INTERNAL_SERVER_ERROR)

    throw ServiceException(DetailErrorCode(message, status))
  }

  // 내부 서버 오류 발생시 에러를 노출시키지 않기 위해서 로깅만 사용한다.
  fun rest5xxHandle(): (request: HttpRequest, response: ClientHttpResponse) -> Unit = { req, res ->
    val id = UUID.randomUUID().toString()
    printRequest(id, req)
    printResponse(id, res)
    throw ServiceException(CommonErrorCode.INTERNAL_SERVER_ERROR)
  }

  private fun printRequest(sessionNumber: String, req: HttpRequest) {
//    val bodyString = String(body, StandardCharsets.UTF_8)
    log.warn("[${sessionNumber}] URI: ${req.uri}, Method: ${req.method}, Headers:${req.headers}")
  }

  private fun printResponse(sessionNumber: String, res: ClientHttpResponse) {
    val body = BufferedReader(
      InputStreamReader(res.body, StandardCharsets.UTF_8)
    ).lines()
      .collect(Collectors.joining("\n"))

    log.warn("[${sessionNumber}] Status: ${res.statusCode}, Headers:${res.headers}, Body:${body}")
  }
}
