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
  fun getInvitation(
    userId: String,
    chatRoomId: String,
  ): InvitationResponse {
    val key = generateKey(userId, chatRoomId)
    var value = redisTemplate.opsForValue().get(key)
    if (value == null) {
      value = setInvitation(key, chatRoomId)
    }

    val expiration = redisTemplate.getExpire(key)

    return InvitationResponse(value, LocalDateTime.now().plusSeconds(expiration).toString())
  }

  fun setInvitation(
    key: String,
    chatRoomId: String,
  ): String {
    val invitationCode = generateCode()
    val ops: ValueOperations<String, String> = redisTemplate.opsForValue()
    ops.set(key, invitationCode, propertyConfig.getExpiration())
    invitationChatRoomRepository.setInvitationCode(invitationCode, chatRoomId)
    return invitationCode
  }

  private fun generateKey(
    userId: String,
    chatRoomId: String,
  ): String {
    return "$userId:$chatRoomId"
  }

  private fun generateCode(): String {
    return UUID.randomUUID().toString()
  }
}
