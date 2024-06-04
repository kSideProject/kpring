package kpring.chat.chatroom.repository

import kpring.chat.global.config.PropertyConfig
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserChatRoomInvitationRepository(
  private val redisTemplate: RedisTemplate<String, String>,
  private val propertyConfig: PropertyConfig,
  private val invitationChatRoomRepository: InvitationChatRoomRepository,
) {
  fun getInvitationCode(
    userId: String,
    chatRoomId: String,
  ): String {
    val key = generateKey(userId, chatRoomId)
    var value = redisTemplate.opsForValue().get(key)
    if (value == null) {
      value = setInvitation(key, chatRoomId)
    }
    return value
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

  fun getExpiration(
    userId: String,
    chatRoomId: String,
  ): Long{
    val key = chatRoomId
    return redisTemplate.getExpire(key)
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
