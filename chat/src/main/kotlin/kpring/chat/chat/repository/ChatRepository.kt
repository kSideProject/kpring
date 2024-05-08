package kpring.chat.chat.repository

import kpring.chat.chat.model.Chat
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository : MongoRepository<Chat,String>, QuerydslPredicateExecutor<Chat>{
}