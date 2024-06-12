package kpring.core.server.dto.response

import kpring.core.server.dto.CategoryInfo
import kpring.core.server.dto.ServerThemeInfo

data class CreateServerResponse(
  val serverId: String,
  val serverName: String,
  val theme: ServerThemeInfo,
  val categories: List<CategoryInfo>,
)
