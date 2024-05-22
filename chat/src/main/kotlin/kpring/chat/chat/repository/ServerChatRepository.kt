package kpring.chat.chat.repository

import kpring.chat.chat.model.ServerChat
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository

@Repository
interface ServerChatRepository : MongoRepository<ServerChat, String>, QuerydslPredicateExecutor<ServerChat>
