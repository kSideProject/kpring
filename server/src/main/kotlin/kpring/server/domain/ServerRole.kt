package kpring.server.domain

enum class ServerRole(
  private val authorities: Set<ServerAuthority>,
) {
  OWNER(setOf(ServerAuthority.DELETE, ServerAuthority.INVITE)),
  ;

  fun contains(role: ServerAuthority): Boolean = authorities.contains(role)
}
