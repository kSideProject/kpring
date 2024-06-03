package kpring.server.domain

enum class ServerRole(
  private val authorities: Set<ServerAuthority>,
) {
  OWNER(setOf(ServerAuthority.DELETE, ServerAuthority.INVITE)),
  MEMBER(setOf()),
  ;

  fun contains(role: ServerAuthority): Boolean = authorities.contains(role)
}
