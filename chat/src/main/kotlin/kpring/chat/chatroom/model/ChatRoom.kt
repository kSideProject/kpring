package kpring.chat.chatroom.model

import kpring.chat.global.model.BaseTime
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "chatrooms")
class ChatRoom(
  @Id val id: String? = null,
  val members: MutableSet<String> = mutableSetOf(),
) : BaseTime() {
  fun getUsers(): Set<String> {
    return members
  }

  fun addUsers(list: List<String>) {
    members.addAll(list)
  }

  fun addUser(userId: String) {
    members.add(userId)
  }

  fun removeUser(userId: String) {
    members.remove(userId)
  }
}
