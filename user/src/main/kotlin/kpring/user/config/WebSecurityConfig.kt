package kpring.user.config

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig {
  @Bean
  fun encode(): PasswordEncoder {
    return BCryptPasswordEncoder()
  }

  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http.csrf { csrf -> csrf.disable() }

    http.authorizeHttpRequests { authorizeHttpRequests ->
      authorizeHttpRequests.requestMatchers(
        PathRequest.toStaticResources().atCommonLocations(),
      ).permitAll()
        .anyRequest().permitAll()
    }
    return http.build()
  }
}
