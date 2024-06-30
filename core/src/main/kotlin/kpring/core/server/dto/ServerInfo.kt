package kpring.core.server.dto

data class ServerInfo(
  val id: String,
  val name: String,
  val users: List<ServerUserInfo>,
  val theme: ServerThemeInfo,
  val categories: List<CategoryInfo>,
)
