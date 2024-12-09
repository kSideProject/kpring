package kpring.user.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration

@Configuration
@EnableWebSecurity
class SecurityConfig {
  @Bean
  fun encode(): PasswordEncoder {
    return BCryptPasswordEncoder()
  }

  @Bean
  fun filterChain(http: HttpSecurity): SecurityFilterChain {
    http
      .cors { cors ->
        cors.configurationSource {
          val cors = CorsConfiguration()
          cors.allowedOrigins = listOf("http://localhost:3000")
          cors.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "PATCH")
          cors.allowedHeaders = listOf("*")
          cors.allowCredentials = true
          cors
        }
      }
      .csrf { it.disable() }
      .formLogin { it.disable() }
      .httpBasic { }

    return http.build()
  }
}
