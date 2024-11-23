package kpring.chat.chatroom.model

import kpring.chat.NoArg
import kpring.chat.global.model.BaseTime
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@NoArg
@Document(collection = "chatrooms")
class ChatRoom(
  @Id val id: String? = null,
  var ownerId: String? = null,
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

  fun transferOwnership(newOwnerId: String) {
    ownerId = newOwnerId
  }
}
