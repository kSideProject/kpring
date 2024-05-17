package kpring.core.auth.config

import com.fasterxml.jackson.databind.ObjectMapper
import kpring.core.auth.client.AuthClient
import kpring.core.global.dto.response.ApiResponse
import kpring.core.global.exception.CommonErrorCode
import kpring.core.global.exception.DetailErrorCode
import kpring.core.global.exception.ServiceException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.client.JdkClientHttpRequestFactory
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import java.net.http.HttpClient
import java.time.Duration

@Configuration
class AuthClientConfig {
  @Value("\${auth.url}")
  private lateinit var authUrl: String

  @Value("\${auth.read-timeout:1s}")
  private lateinit var readTimeout: Duration

  @Value("\${auth.connect-timeout:5s}")
  private lateinit var connectTimeout: Duration

  @Autowired
  private lateinit var objectMapper: ObjectMapper

  @Bean
  fun authClient(): AuthClient {

    val client = HttpClient.newBuilder()
      .connectTimeout(connectTimeout)
      .version(HttpClient.Version.HTTP_2)
      .build()

    val fac = JdkClientHttpRequestFactory(client)
    fac.setReadTimeout(readTimeout)
    val type =
      objectMapper.typeFactory.constructParametricType(ApiResponse::class.java, Any::class.java)

    val restClient =
      RestClient.builder()
        .requestFactory(fac)
        .baseUrl(authUrl)
        .defaultStatusHandler(HttpStatusCode::is4xxClientError) { req, res ->
          val response: ApiResponse<Any> = objectMapper.readValue(res.body, type)
          val status = HttpStatus.valueOf(res.statusCode.value())
          val message = if (response.message != null) {
            response.message
          } else {
            // 실패 응답시 message가 없는 경우에는 api 스펙을 잘못 사용한 경우이므로 서버 오류로 처리한다.
            throw ServiceException(CommonErrorCode.INTERNAL_SERVER_ERROR)
          }

          throw ServiceException(DetailErrorCode(message, status))
        }
        .defaultStatusHandler(HttpStatusCode::is5xxServerError) { req, res ->
          // 내부 서버 오류 발생시 에러를 노출시키지 않기 위해서 로깅만 사용한다.
          val response: ApiResponse<Any> = objectMapper.readValue(res.body, type)
          val status = HttpStatus.valueOf(res.statusCode.value())
          val message = response.message
          throw ServiceException(CommonErrorCode.INTERNAL_SERVER_ERROR)
        }
        .build()

    restClient.get().retrieve()

    val adapter = RestClientAdapter.create(restClient)
    val factory = HttpServiceProxyFactory
      .builderFor(adapter)
      .build()

    return factory.createClient(AuthClient::class.java)
  }
}
