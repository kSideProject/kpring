package kpring.server.config

import kpring.core.auth.config.AuthClientConfig
import kpring.core.global.controller.ServiceExceptionRestController
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Import(AuthClientConfig::class)
@Configuration
class CoreConfiguration {

  @Bean
  fun serviceExceptionRestController(): ServiceExceptionRestController {
    return ServiceExceptionRestController()
  }
}
