package kpring.chat.chatroom.repository

import kpring.chat.chatroom.model.ChatRoom
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRoomRepository : MongoRepository<ChatRoom, String> {
  fun existsByIdAndMembersContaining(
    roomId: String,
    userId: String,
  ): Boolean

  fun existsByIdAndOwnerId(
    roomId: String,
    ownerId: String,
  ): Boolean
}
