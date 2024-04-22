package kpring.chat.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "chats")
class Chat(
    val room: String,
    val nickname: String,
    val content: String
) {
    @Id
    var id: String? = null

    var isDeleted: Boolean = false

    var sentAt: LocalDateTime = LocalDateTime.now()

    fun deleted() {
        isDeleted = true
    }
}
