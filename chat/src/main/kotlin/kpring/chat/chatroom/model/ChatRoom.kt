package kpring.chat.chatroom.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "chatrooms")
class ChatRoom(
    val createdAt: LocalDateTime
){
    @Id
    var id: String? = null

    var members : MutableList<String> = mutableListOf()


    fun getUsers(): List<String>{
        return members
    }

    fun addUsers(list : List<String>){
        members.addAll(list)
    }

}