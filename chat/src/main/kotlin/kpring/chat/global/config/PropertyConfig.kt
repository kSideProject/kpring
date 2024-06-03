package kpring.chat.global.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
@ConfigurationProperties(prefix = "chatroom")
class PropertyConfig {
  private lateinit var expiration: Duration

  fun getExpiration(): Duration {
    return expiration
  }

  fun setExpiration(expiration: Duration) {
    this.expiration = expiration
  }
}
