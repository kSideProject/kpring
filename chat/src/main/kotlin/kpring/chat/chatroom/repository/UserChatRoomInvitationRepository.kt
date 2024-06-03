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

    val expiration = getExpiration(key)

    return InvitationResponse(value, LocalDateTime.now().plusSeconds(expiration).toString())
  }

  fun setInvitation(
    key: String,
    chatRoomId: String,
  ): String {
    val invitationLink = generateLink()
    val ops: ValueOperations<String, String> = redisTemplate.opsForValue()
    ops.set(key, invitationLink, propertyConfig.getExpiration())
    invitationChatRoomRepository.setInvitationLink(invitationLink, chatRoomId)
    return invitationLink
  }

  private fun generateKey(
    userId: String,
    chatRoomId: String,
  ): String {
    return "$userId:$chatRoomId"
  }

  private fun generateLink(): String {
    return UUID.randomUUID().toString()
  }

  private fun getExpiration(key: String): Long {
    val expiration = redisTemplate.getExpire(key)
    if (expiration == null || expiration == -1L) {
      throw GlobalException(ErrorCode.GET_EXPIRATION_FAILURE)
    }
    return expiration
  }
}
