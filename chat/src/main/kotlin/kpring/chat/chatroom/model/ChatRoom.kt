package kpring.chat.chatroom.model

import kpring.chat.global.model.BaseTime
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "chatrooms")
class ChatRoom : BaseTime() {
  @Id
  var id: String? = null

  var members: MutableSet<String> = mutableSetOf()

  fun getUsers(): List<String> {
    return members.stream().toList()
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
