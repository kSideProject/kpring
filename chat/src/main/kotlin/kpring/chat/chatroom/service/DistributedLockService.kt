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

}
