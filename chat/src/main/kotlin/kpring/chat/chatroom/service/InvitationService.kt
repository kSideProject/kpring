package kpring.chat.chatroom.service

import kpring.chat.chatroom.repository.InvitationRepository
import kpring.chat.global.config.ChatRoomProperty
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*

@Service
class InvitationService(
  private val invitationRepository: InvitationRepository,
  private val chatRoomProperty: ChatRoomProperty,
) {
  fun getInvitation(
    userId: String,
    chatRoomId: String,
  ): String? {
    val key = generateKey(userId, chatRoomId)
    return invitationRepository.getValue(key)
  }

  fun setInvitation(
    userId: String,
    chatRoomId: String,
  ): String {
    val key = generateKey(userId, chatRoomId)
    val value = generateValue()
    invitationRepository.setValueAndExpiration(key, value, chatRoomProperty.getExpiration())
    return value
  }

  fun generateKeyAndCode(
    userId: String,
    chatRoomId: String,
    code: String,
  ): String {
    val key = generateKey(userId, chatRoomId)
    return generateCode(key, code)
  }

  private fun generateCode(
    key: String,
    value: String,
  ): String {
    val combinedString = "$key,$value"
    val digest = MessageDigest.getInstance("SHA-256")
    val hash = digest.digest(combinedString.toByteArray(StandardCharsets.UTF_8))
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
