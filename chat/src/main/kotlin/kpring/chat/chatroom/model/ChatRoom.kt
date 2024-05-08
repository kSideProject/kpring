package kpring.chat.chatroom.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "chatrooms")
class ChatRoom {
  @Id
  var id: String? = null

  var members: MutableList<String> = mutableListOf()

  val createdAt: LocalDateTime = LocalDateTime.now()

  fun getUsers(): List<String> {
    return members
  }

  fun addUsers(list: List<String>) {
    members.addAll(list)
  }
}
