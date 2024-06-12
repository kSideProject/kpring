package kpring.core.server.dto

data class ServerSimpleInfo(
  val id: String,
  val name: String,
  val bookmarked: Boolean,
  val categories: List<CategoryInfo>,
  val theme: ServerThemeInfo,
)
