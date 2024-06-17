package kpring.chat.chatroom.service

import kpring.chat.chatroom.dto.InvitationInfo
import kpring.chat.chatroom.repository.InvitationRepository
import kpring.chat.global.config.ChatRoomProperty
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
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
    return encodeCode(key, code)
  }

  private fun decodeCode(encodedString: String): String {
    val decodedBytes = Base64.getUrlDecoder().decode(encodedString)
    val decodedString = String(decodedBytes, StandardCharsets.UTF_8)
    return decodedString
  }

  fun getInvitationInfoFromCode(code: String): InvitationInfo {
    val decodedCode = decodeCode(code)
    val keyAndValue: List<String> = decodedCode.split(",")
    val userIdAndChatRoomId: List<String> = keyAndValue[0].split(":")
    return InvitationInfo(userIdAndChatRoomId[0], userIdAndChatRoomId[1], keyAndValue[1])
  }

  private fun encodeCode(
    key: String,
    value: String,
  ): String {
    val combinedString = "$key,$value"
    val encodedString = Base64.getUrlEncoder().withoutPadding().encodeToString(combinedString.toByteArray(StandardCharsets.UTF_8))
    return encodedString
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
