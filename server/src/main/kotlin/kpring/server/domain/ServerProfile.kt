package kpring.server.domain

class ServerProfile(
  val userId: String,
  val server: Server,
  val role: ServerRole = ServerRole.MEMBER,
  val bookmarked: Boolean = false,
) {
  /**
   * @param userId 권한을 확인할 유저의 id
   * @param authority 확인할 권한
   * @return 권한이 있으면 true, 없으면 false
   */
  fun hasRole(
    userId: String,
    authority: ServerAuthority,
  ): Boolean {
    return role.contains(authority)
  }

  fun dontHasRole(
    userId: String,
    authority: ServerAuthority,
  ): Boolean {
    return !hasRole(userId, authority)
  }
}
