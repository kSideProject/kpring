package kpring.server.domain

import kpring.core.global.exception.ServiceException
import kpring.server.error.ServerErrorCode

class Server(
  val id: String,
  val name: String,
  val users: MutableSet<ServerUser> = mutableSetOf(),
  val invitedUserIds: MutableSet<String> = mutableSetOf(),
  private val authorities: Map<String, ServerAuthority> = mapOf(),
) {

  private fun isInvited(userId: String): Boolean {
    return invitedUserIds.contains(userId)
  }

  /**
   * @param userId 권한을 확인할 유저의 id
   * @param authority 확인할 권한
   * @return 권한이 있으면 true, 없으면 false
   */
  fun hasRole(userId: String, authority: ServerAuthority): Boolean {
    return authorities[userId]?.contains(authority) ?: false
  }

  /**
   * @param user 초대 받은 유저
   * @throws ServiceException 유저가 초대되지 않았을 경우
   */
  fun addUser(user: ServerUser) {
    if (this.isInvited(user.id)) {
      invitedUserIds.remove(user.id)
      users.add(user)
    } else {
      throw ServiceException(ServerErrorCode.USER_NOT_INVITED)
    }
  }

  /**
   * @param userId 초대할 유저의 id
   */
  fun registerInvitation(userId: String) {
    val user = users.find { it.id == userId }

    if (user == null) {
      invitedUserIds.add(userId)
    } else {
      throw ServiceException(ServerErrorCode.ALREADY_REGISTERED_USER)
    }
  }
}
