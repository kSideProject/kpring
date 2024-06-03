package kpring.chat.chatroom.repository

import kpring.chat.global.config.PropertyConfig
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Component

@Component
class InvitationChatRoomRepository
