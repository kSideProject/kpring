package kpring.core.server.config

import kpring.core.global.exception.HttpClientErrorHandle.rest4xxHandle
import kpring.core.global.exception.HttpClientErrorHandle.rest5xxHandle
import kpring.core.server.client.ServerClient
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
class ServerClientConfig {
  private val valField: String? = null

  @Value("\${url.server}")
  private lateinit var serverUrl: String

  @Value("\${server.read-timeout:1s}")
  private lateinit var readTimeout: Duration

  @Value("\${server.connect-timeout:5s}")
  private lateinit var connectTimeout: Duration

  @Bean
  fun serverClient(): ServerClient {
    println(valField)
    val client = HttpClient.newBuilder().connectTimeout(connectTimeout).version(HttpClient.Version.HTTP_2).build()

    val fac = JdkClientHttpRequestFactory(client)
    fac.setReadTimeout(readTimeout)

    val restClient =
      RestClient.builder().requestFactory(fac).baseUrl(serverUrl)
        .defaultStatusHandler(HttpStatusCode::is4xxClientError, rest4xxHandle())
        .defaultStatusHandler(HttpStatusCode::is5xxServerError, rest5xxHandle()).build()

    val adapter = RestClientAdapter.create(restClient)
    val factory = HttpServiceProxyFactory.builderFor(adapter).build()

    return factory.createClient(ServerClient::class.java)
  }
}
