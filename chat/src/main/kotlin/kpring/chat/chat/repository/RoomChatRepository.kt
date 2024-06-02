package kpring.chat.chat.repository

import kpring.chat.chat.model.RoomChat
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository

@Repository
interface RoomChatRepository : MongoRepository<RoomChat, String>, QuerydslPredicateExecutor<RoomChat> {
  fun findAllByRoomId(
    roomId: String,
    pageable: Pageable,
  ): List<RoomChat>
}
