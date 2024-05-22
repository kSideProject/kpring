package kpring.chat.chat.model

import kpring.chat.NoArg
import kpring.chat.global.model.BaseTime
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@NoArg
@Document(collection = "server_chats")
class ServerChat(
  userId: String,
  val serverId: String,
  val content: String,
) : BaseTime() {
  @Id
  var id: String? = null

  fun isEdited(): Boolean {
    return !createdAt.equals(updatedAt)
  }
}
