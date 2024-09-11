package kpring.chat.chat.repository

import kpring.chat.chat.model.Chat
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface ServerChatRepository : MongoRepository<Chat, String>, QuerydslPredicateExecutor<Chat> {
  fun findAllByContextId(serverId: String): List<Chat>
}
