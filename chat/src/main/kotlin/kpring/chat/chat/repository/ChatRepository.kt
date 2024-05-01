package kpring.chat.chat.repository

import kpring.chat.chat.model.Chat
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatRepository : MongoRepository<Chat,String> {
    fun findAllByRoomId(roomId : String): List<Chat>
}