package kpring.chat.chat.model

import lombok.Getter
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Getter
@Document(collection = "chats")
class Chat(
    val userId: String,
    val roomId: String,
    val content: String,
    val sentAt: LocalDateTime = LocalDateTime.now()
) {
    @Id
    var id: String? = null

    var isDeleted: Boolean = false
    var isEdited: Boolean = false

    fun delete() {
        isDeleted = true
    }

    fun edit(){
        isEdited = true;
    }
}
