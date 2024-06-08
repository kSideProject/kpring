package kpring.chat.chatroom.service

import kpring.chat.chatroom.dto.Lock
import kpring.chat.chatroom.model.DistributedLock
import kpring.chat.chatroom.repository.DistributedLockRepository
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import org.springframework.stereotype.Service
import java.util.*

@Service
class DistributedLockService(
  private val lockRepository: DistributedLockRepository,
) {
  fun acquireLock(
    lockId: String,
    owner: String,
    expireInMillis: Long,
  ): Boolean {
    val now = System.currentTimeMillis()
    val expiresAt = now + expireInMillis

    val optionalLock = lockRepository.findById(lockId)
    return if (optionalLock.isPresent) {
      val lock = optionalLock.get()
      if (lock.expiresAt < now) {
        lockRepository.save(DistributedLock(lockId, owner, expiresAt))
        true
      } else {
        false
      }
    } else {
      lockRepository.save(DistributedLock(lockId, owner, expiresAt))
      true
    }
  }

  fun releaseLock(
    lockId: String,
    owner: String,
  ) {
    val optionalLock = lockRepository.findById(lockId)
    if (optionalLock.isPresent && optionalLock.get().owner == owner) {
      lockRepository.deleteById(lockId)
    }
  }

  fun getLock(chatRoomId: String): Lock {
    val lockId = "chatRoom:$chatRoomId:lock"
    val owner = UUID.randomUUID().toString()

    val lockAcquired = acquireLock(lockId, owner, 10000) // 10초 동안 잠금 유지
    if (!lockAcquired) {
      throw GlobalException(ErrorCode.CONCURRENCY_CONFLICTION)
    }
    return Lock(lockId = lockId, owner = owner, acquired = lockAcquired)
  }
}
