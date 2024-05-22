package kpring.server.domain

import kpring.core.global.exception.ServiceException
import kpring.server.error.ServerErrorCode

class Server(
  val id: String,
  val name: String,
  val users: MutableSet<String> = mutableSetOf(),
  val invitedUserIds: MutableSet<String> = mutableSetOf(),
) {
  private fun isInvited(userId: String): Boolean {
    return invitedUserIds.contains(userId)
  }

  /**
   * @param user 초대 받은 유저
   * @throws ServiceException 유저가 초대되지 않았을 경우
   */
  fun addUser(
    userId: String,
    name: String,
    imagePath: String,
  ): ServerProfile {
    if (this.isInvited(userId)) {
      invitedUserIds.remove(userId)
      users.add(userId)
    } else {
      throw ServiceException(ServerErrorCode.USER_NOT_INVITED)
    }
    return ServerProfile(userId, name, imagePath, this)
  }

  /**
   * @param userId 초대할 유저의 id
   * @throws ServiceException 이미 가입한 등록된 유저일 경우
   */
  fun registerInvitation(userId: String) {
    if (!users.contains(userId)) {
      invitedUserIds.add(userId)
    } else {
      throw ServiceException(ServerErrorCode.ALREADY_REGISTERED_USER)
    }
  }
}
