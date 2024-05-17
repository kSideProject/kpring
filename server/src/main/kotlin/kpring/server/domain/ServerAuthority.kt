package kpring.server.domain

enum class ServerAuthority {
  DELETE,
  INVITE,
  ;

  fun contains(authority: ServerAuthority): Boolean {
    return this == authority
  }
}
