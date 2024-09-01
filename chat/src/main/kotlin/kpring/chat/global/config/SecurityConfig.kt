package kpring.chat.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {
  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http
      .authorizeHttpRequests { auth ->
        auth
          .requestMatchers("/ws/**").permitAll() // WebSocket 경로를 인증 없이 허용
          .anyRequest().authenticated() // 나머지 요청은 인증 필요
      }
      .csrf { csrf -> csrf.disable() } // CSRF 보호 비활성화
      .cors { cors -> cors.disable() } // CORS 비활성화
      .httpBasic { basic -> basic.disable() } // 기본 인증 비활성화
    return http.build()
  }
}
