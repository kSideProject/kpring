package kpring.chat.chatroom.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "chatrooms")
class ChatRoom(
    var members : MutableList<String>
){
    @Id
    var id: String? = null

    val createdAt: LocalDateTime = LocalDateTime.now()

    fun getUsers(): MutableList<String>{
        return members
    }

    fun addUsers(list : MutableList<String>){
        members.addAll(list)
    }

}