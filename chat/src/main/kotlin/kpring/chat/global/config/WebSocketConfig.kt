package kpring.chat.global.config

import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.auth.client.AuthClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessageType
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(
  val authClient: AuthClient,
) : WebSocketMessageBrokerConfigurer {
  @Value("\${url.front}")
  val frontUrl: String = ":63343"

  override fun registerStompEndpoints(registry: StompEndpointRegistry) {
    registry.addEndpoint("/ws").setAllowedOrigins(frontUrl).withSockJS()
  }

  override fun configureMessageBroker(config: MessageBrokerRegistry) {
    config.setApplicationDestinationPrefixes("/app")
    config.enableSimpleBroker("/topic")
  }

  override fun configureWebSocketTransport(registry: WebSocketTransportRegistration) {
    registry.setMessageSizeLimit(4 * 8192)
    registry.setTimeToFirstMessage(30000)
  }

  @Bean
  fun webSocketAuthInterceptor(): ChannelInterceptor {
    return object : ChannelInterceptor {
      override fun preSend(
        message: Message<*>,
        channel: MessageChannel,
      ): Message<*>? {
        val simpMessageType = SimpMessageHeaderAccessor.getMessageType(message.headers)
        if (simpMessageType == SimpMessageType.CONNECT) {
          val token = SimpMessageHeaderAccessor.wrap(message).getFirstNativeHeader("Authorization")
          if (token != null) {
            val userId = authClient.getTokenInfo(token).data!!.userId
            val principal = UsernamePasswordAuthenticationToken(userId, null, emptyList())
            SimpMessageHeaderAccessor.getAccessor(message, SimpMessageHeaderAccessor::class.java)!!.user = principal
          } else {
            throw GlobalException(ErrorCode.MISSING_TOKEN)
          }
        }
        return message
      }
    }
  }

  override fun configureClientInboundChannel(registration: ChannelRegistration) {
    registration.interceptors(webSocketAuthInterceptor())
  }

  @Bean
  fun corsFilter(): CorsFilter {
    val config = CorsConfiguration()
    config.allowCredentials = true
    config.addAllowedOrigin(frontUrl)
    config.addAllowedHeader("*")
    config.addAllowedMethod("*")

    val source: UrlBasedCorsConfigurationSource = UrlBasedCorsConfigurationSource()
    source.registerCorsConfiguration("/**", config)
    return CorsFilter(source)
  }
}
