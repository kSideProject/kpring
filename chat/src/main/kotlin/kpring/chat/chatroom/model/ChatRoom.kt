package kpring.chat.chatroom.model

import kpring.chat.NoArg
import kpring.chat.global.model.BaseTime
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@NoArg
@Document(collection = "chatrooms")
class ChatRoom : BaseTime() {
  @Id
  var id: String? = null

  var members: MutableList<String> = mutableListOf()

  fun getUsers(): List<String> {
    return members
  }

  fun addUsers(list: List<String>) {
    members.addAll(list)
  }

  fun removeUser(userId: String) {
    members.remove(userId)
  }
}
