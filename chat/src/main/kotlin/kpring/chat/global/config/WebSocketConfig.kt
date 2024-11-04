package kpring.chat.global.config

import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.chat.global.util.AccessVerifier
import kpring.core.auth.client.AuthClient
import kpring.core.chat.model.ChatType
import kpring.core.server.client.ServerClient
import kpring.core.server.dto.request.GetServerCondition
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
  val serverClient: ServerClient,
  val accessVerifier: AccessVerifier,
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
  fun webSocketInterceptor(): ChannelInterceptor {
    return object : ChannelInterceptor {
      override fun preSend(
        message: Message<*>,
        channel: MessageChannel,
      ): Message<*> {
        val simpMessageType = SimpMessageHeaderAccessor.getMessageType(message.headers)
        return when (simpMessageType) {
          SimpMessageType.CONNECT -> handleConnectMessage(message)
          SimpMessageType.SUBSCRIBE -> {
            handleSubscribeMessage(message)
            message
          }
          else -> message
        }
      }
    }
  }

  private fun handleConnectMessage(message: Message<*>): Message<*> {
    val headerAccessor = SimpMessageHeaderAccessor.wrap(message)
    val token =
      headerAccessor.getFirstNativeHeader("Authorization")
        ?.removePrefix("Bearer ")
        ?: throw GlobalException(ErrorCode.MISSING_TOKEN)

    val userId =
      authClient.getTokenInfo(token).data?.userId
        ?: throw GlobalException(ErrorCode.INVALID_TOKEN)

    val principal = UsernamePasswordAuthenticationToken(userId, null, emptyList())
    SimpMessageHeaderAccessor.getAccessor(message, SimpMessageHeaderAccessor::class.java)?.user = principal

    return message
  }

  private fun handleSubscribeMessage(message: Message<*>) {
    val headerAccessor = SimpMessageHeaderAccessor.wrap(message)

    val token =
      headerAccessor.getFirstNativeHeader("Authorization")
        ?.removePrefix("Bearer ")
        ?: throw GlobalException(ErrorCode.MISSING_TOKEN)

    val contextId =
      headerAccessor.getFirstNativeHeader("contextId")
        ?: throw GlobalException(ErrorCode.MISSING_CONTEXTID)

    val context =
      headerAccessor.getFirstNativeHeader("context")
        ?: throw GlobalException(ErrorCode.MISSING_CONTEXT)

    val userId =
      authClient.getTokenInfo(token).data?.userId
        ?: throw GlobalException(ErrorCode.INVALID_TOKEN)

    when (context) {
      ChatType.ROOM.toString() -> {
        accessVerifier.verifyChatRoomAccess(contextId, userId)
      }
      ChatType.SERVER.toString() -> {
        val serverList =
          serverClient.getServerList(token, GetServerCondition())
            .body?.data ?: throw GlobalException(ErrorCode.SERVER_ERROR)
        accessVerifier.verifyServerAccess(serverList, contextId)
      }
      else -> throw GlobalException(ErrorCode.INVALID_CONTEXT)
    }
  }

  override fun configureClientInboundChannel(registration: ChannelRegistration) {
    registration.interceptors(webSocketInterceptor())
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
