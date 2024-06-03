package kpring.chat.chatroom.repository

import kpring.chat.global.config.PropertyConfig
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Component

@Component
class InvitationChatRoomRepository(
  private val redisTemplate: RedisTemplate<String, String>,
  private val propertyConfig: PropertyConfig,
) {
  fun setInvitationLink(
    invitationLink: String,
    chatRoomId: String,
  ) {
    val ops: ValueOperations<String, String> = redisTemplate.opsForValue()
    val result = ops.setIfAbsent(invitationLink, chatRoomId, propertyConfig.getExpiration())

    if (result == null || !result) {
      throw GlobalException(ErrorCode.INVITATION_LINK_SAVE_FAILURE)
    }
  }
}
