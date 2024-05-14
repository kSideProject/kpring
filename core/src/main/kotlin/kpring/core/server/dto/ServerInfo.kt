package kpring.core.server.dto

data class ServerInfo(
  val id: String,
  val name: String,
  val users: List<ServerUserInfo>,
)
