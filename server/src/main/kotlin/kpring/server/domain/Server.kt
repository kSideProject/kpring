package kpring.server.domain

import kpring.core.global.exception.ServiceException
import kpring.server.error.ServerErrorCode

class Server(
  val id: String,
  val name: String,
  val users: MutableSet<String> = mutableSetOf(),
  val invitedUserIds: MutableSet<String> = mutableSetOf(),
  val authorities: Map<String, ServerRole> = mapOf(),
) {
  private fun isInvited(userId: String): Boolean {
    return invitedUserIds.contains(userId)
  }

  /**
   * @param userId 권한을 확인할 유저의 id
   * @param authority 확인할 권한
   * @return 권한이 있으면 true, 없으면 false
   */
  fun hasRole(
    userId: String,
    authority: ServerAuthority,
  ): Boolean {
    return authorities[userId]?.contains(authority) ?: false
  }

  fun dontHasRole(
    userId: String,
    authority: ServerAuthority,
  ): Boolean {
    return !hasRole(userId, authority)
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

  fun verifyIfJoined(userId: String): Boolean {
    val user = users.find { it == userId }

    if (user == null) {
      throw ServiceException(ServerErrorCode.USER_NOT_AUTHORIZED)
    }

    return true
  }
}
