package kpring.chat.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {
  override fun registerStompEndpoints(registry: StompEndpointRegistry) {
    registry.addEndpoint("/ws")
      .withSockJS()
  }

  override fun configureMessageBroker(config: MessageBrokerRegistry) {
    config.setApplicationDestinationPrefixes("/app")
    config.enableSimpleBroker("/topic")
  }

  override fun configureWebSocketTransport(registry: WebSocketTransportRegistration) {
    registry.setMessageSizeLimit(4 * 8192)
    registry.setTimeToFirstMessage(30000)
  }
}
