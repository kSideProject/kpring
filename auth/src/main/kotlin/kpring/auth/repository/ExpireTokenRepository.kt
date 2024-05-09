package kpring.auth.repository

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.LocalDateTime

@Component
class ExpireTokenRepository(
  val redisTemplate: ReactiveStringRedisTemplate,
) {
  suspend fun isExpired(tokenId: String): Boolean {
    return redisTemplate.getExpire(tokenId)
      .map { !it.isZero }
      .defaultIfEmpty(false)
      .awaitSingle()
  }

  suspend fun expireToken(
    tokenId: String,
    expiredAt: LocalDateTime,
  ) {
    val now = LocalDateTime.now()
    if (expiredAt.isBefore(now)) return
    val ttl = Duration.between(now, expiredAt)
    redisTemplate.opsForValue().setIfAbsent(tokenId, "")
      .flatMap {
        if (it) redisTemplate.expire(tokenId, ttl) else Mono.just(false)
      }.awaitSingle()
  }
}
