package kpring.server.domain

class ServerProfile(
  val userId: String,
  val name: String,
  val imagePath: String,
  val server: Server,
  val role: ServerRole = ServerRole.MEMBER,
  val bookmarked: Boolean = false,
) {
  /**
   * @param authority 확인할 권한
   * @return 권한이 있으면 true, 없으면 false
   */
  fun hasRole(authority: ServerAuthority): Boolean {
    return role.contains(authority)
  }

  /**
   * hasRole 역 연산자
   */
  fun dontHasRole(authority: ServerAuthority): Boolean {
    return !hasRole(authority)
  }
}
