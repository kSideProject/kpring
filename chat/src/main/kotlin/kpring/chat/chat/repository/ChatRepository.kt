package kpring.chat.chat.repository

import kpring.chat.chat.model.Chat
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository : MongoRepository<Chat,String> {
    fun findByUserId(userId: String, pageable: Pageable): List<Chat>
}