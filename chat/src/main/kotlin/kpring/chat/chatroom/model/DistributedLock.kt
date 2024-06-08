package kpring.chat.chatroom.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "distributedLocks")
data class DistributedLock(
  @Id
  val id: String,
  val owner: String,
  val expiresAt: Long,
)
