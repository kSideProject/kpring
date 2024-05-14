package kpring.chat.chat.model

import jakarta.persistence.Entity
import kpring.chat.NoArg
import kpring.chat.global.model.BaseTime
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@NoArg
@Entity
@Document(collection = "chats")
class Chat(
  val userId: String,
  val roomId: String,
  val content: String,
) : BaseTime() {
  @Id
  var id: String? = null

  fun isEdited(): Boolean {
    return !createdAt.equals(updatedAt)
  }
}
