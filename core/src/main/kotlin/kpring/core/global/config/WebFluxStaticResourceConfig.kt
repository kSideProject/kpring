package kpring.core.global.config

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.ResourceHandlerRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer

@Profile("local")
@Configuration
class WebFluxStaticResourceConfig : WebFluxConfigurer {
  private val logger = LoggerFactory.getLogger("test")

  override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
    logger.info("test run configuration")
    registry.addResourceHandler("/static/**")
      .addResourceLocations("classpath:/static/")
  }

  override fun addCorsMappings(registry: CorsRegistry) {
    registry.addMapping("/**")
      .allowedOriginPatterns("*")
      .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH")
  }
}
