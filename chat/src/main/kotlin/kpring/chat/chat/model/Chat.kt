package kpring.chat.chat.model

import kpring.chat.global.model.BaseTimeWithUpdateAndDelete
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "chats")
class Chat(
    val userId: String,
    val roomId: String,
    val content: String,
) : BaseTimeWithUpdateAndDelete() {
    @Id
    var id: String? = null

    var isDeleted: Boolean = false
    var isEdited: Boolean = false

    fun delete() {
        isDeleted = true
    }

    fun edit() {
        isEdited = true
    }
}
