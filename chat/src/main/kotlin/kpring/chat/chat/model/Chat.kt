package kpring.chat.chat.model

import kpring.chat.NoArg
import kpring.chat.global.model.BaseTime
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.mapping.Document

@NoArg
@Document(collection = "chats")
class Chat(
  val userId: String,
  val roomId: String,
  val content: String,
) : BaseTime() {
  @Id
  var id: String? = null

  @Version
  var version: Long? = null

  fun isEdited(): Boolean {
    return !createdAt.equals(updatedAt)
  }
}
