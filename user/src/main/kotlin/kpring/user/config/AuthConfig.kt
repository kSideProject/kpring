package kpring.user.config

import kpring.core.auth.client.AuthClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class AuthConfig {
  @Value("\${auth.url}")
  private val authUrl: String? = null

  @Bean
  fun authClient(): AuthClient {
    val restClient =
      RestClient.builder()
        .baseUrl(authUrl!!)
        .build()
    val adapter = RestClientAdapter.create(restClient)
    val factory = HttpServiceProxyFactory.builderFor(adapter).build()
    return factory.createClient(AuthClient::class.java)
  }
}
