package kpring.chat.chatroom.repository

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*

@Component
class InvitationLinkRepository(
  private val redisTemplate: RedisTemplate<String, String>,
) {
  @Value("\${invitation.expiration}")
  private val expiration: Long = 3600

  fun saveChatRoomIdAndGetLink(chatRoomId: String): String {
    val uniqueId = UUID.randomUUID().toString()
    val key = generateUniqueKey(uniqueId)
    set(key, chatRoomId, Duration.ofSeconds(expiration))
    return generateAddress(uniqueId)
  }

  private fun generateAddress(uniqueId: String): String {
    return "//$uniqueId"
  }

  private fun generateUniqueKey(uniqueId: String): String {
    return "invitation:$uniqueId"
  }

  private fun set(
    key: String,
    value: String,
    duration: Duration,
  ) {
    redisTemplate.opsForValue().set(key, value, duration.seconds, java.util.concurrent.TimeUnit.SECONDS)
  }
}
