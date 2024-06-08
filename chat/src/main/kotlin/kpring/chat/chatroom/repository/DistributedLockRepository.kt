package kpring.chat.chatroom.repository

import kpring.chat.chatroom.model.DistributedLock
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface DistributedLockRepository : MongoRepository<DistributedLock, String>
