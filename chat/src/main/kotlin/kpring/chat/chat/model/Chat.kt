package kpring.chat.chat.model

import kpring.chat.NoArg
import kpring.chat.global.model.BaseTime
import kpring.core.chat.model.ChatType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@NoArg
@Document(collection = "chats")
class Chat(
  @Id
  val id: String? = null,
  val userId: String,
  val type: ChatType,
  // roomId or serverId
  val contextId: String,
  var content: String,
) : BaseTime() {
  fun isEdited(): Boolean {
    return !createdAt.equals(updatedAt)
  }

  fun updateContent(content: String) {
    this.content = content
  }
}
