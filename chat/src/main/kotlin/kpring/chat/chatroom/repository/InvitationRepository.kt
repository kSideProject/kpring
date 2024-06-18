package kpring.chat.chatroom.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class InvitationRepository(
  private val redisTemplate: RedisTemplate<String, String>,
) {
  fun getValue(key: String): String? {
    return redisTemplate.opsForValue().get(key)
  }

  fun setValueAndExpiration(
    key: String,
    value: String,
    expiration: Duration,
  ) {
    val ops: ValueOperations<String, String> = redisTemplate.opsForValue()
    ops.set(key, value, expiration)
  }

  fun getExpiration(key: String): Long {
    return redisTemplate.getExpire(key)
  }
}
