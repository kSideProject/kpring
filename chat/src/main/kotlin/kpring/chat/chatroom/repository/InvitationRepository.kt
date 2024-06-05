package kpring.chat.chatroom.repository

import kpring.chat.global.config.PropertyConfig
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*

@Component
class InvitationRepository(
  private val redisTemplate: RedisTemplate<String, String>,
  private val propertyConfig: PropertyConfig,
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
    return generateCode(key, value)
  }

  fun setInvitation(
    key: String,
    chatRoomId: String,
  ): String {
    val value = generateValue()
    val ops: ValueOperations<String, String> = redisTemplate.opsForValue()
    ops.set(key, value, propertyConfig.getExpiration())
    return value
  }

  fun getExpiration(
    userId: String,
    chatRoomId: String,
  ): Long {
    val key = chatRoomId
    return redisTemplate.getExpire(key)
  }

  fun generateCode(
    key: String,
    value: String,
  ): String {
    val combine = "$key,$value"
    val digest = MessageDigest.getInstance("SHA-256")
    val hash = digest.digest(combine.toByteArray(StandardCharsets.UTF_8))
    return Base64.getEncoder().encodeToString(hash)
  }

  private fun generateKey(
    userId: String,
    chatRoomId: String,
  ): String {
    return "$userId:$chatRoomId"
  }

  private fun generateValue(): String {
    return UUID.randomUUID().toString()
  }
}
