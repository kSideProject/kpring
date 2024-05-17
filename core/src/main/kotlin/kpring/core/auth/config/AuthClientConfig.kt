package kpring.core.auth.config

import com.fasterxml.jackson.databind.ObjectMapper
import kpring.core.auth.client.AuthClient
import kpring.core.global.exception.HttpClientErrorHandle.rest4xxHandle
import kpring.core.global.exception.HttpClientErrorHandle.rest5xxHandle
import org.apache.juli.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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

  private val log = LogFactory.getLog("Auth logger")

  @Bean
  fun authClient(): AuthClient {

    val client = HttpClient.newBuilder()
      .connectTimeout(connectTimeout)
      .version(HttpClient.Version.HTTP_2)
      .build()

    val fac = JdkClientHttpRequestFactory(client)
    fac.setReadTimeout(readTimeout)

    val restClient =
      RestClient.builder()
        .requestFactory(fac)
        .baseUrl(authUrl)
        .defaultStatusHandler(HttpStatusCode::is4xxClientError, rest4xxHandle())
        .defaultStatusHandler(HttpStatusCode::is5xxServerError, rest5xxHandle())
        .build()

    restClient.get().retrieve()

    val adapter = RestClientAdapter.create(restClient)
    val factory = HttpServiceProxyFactory
      .builderFor(adapter)
      .build()

    return factory.createClient(AuthClient::class.java)
  }
}
