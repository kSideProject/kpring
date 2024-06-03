package kpring.chat.chatroom.repository

import kpring.chat.global.config.PropertyConfig
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.chat.chat.dto.response.InvitationResponse
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class UserChatRoomInvitationRepository(
  private val redisTemplate: RedisTemplate<String, String>,
  private val propertyConfig: PropertyConfig,
  private val invitationChatRoomRepository: InvitationChatRoomRepository,
) {
}
