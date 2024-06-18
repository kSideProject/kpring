package kpring.server.util

import kpring.core.server.dto.CategoryInfo
import kpring.core.server.dto.ServerThemeInfo
import kpring.server.domain.Category
import kpring.server.domain.Theme

fun Category.toInfo(): CategoryInfo {
  return CategoryInfo(
    id = this.name,
    name = this.toString(),
  )
}

fun Theme.toInfo(): ServerThemeInfo {
  return ServerThemeInfo(
    id = this.id(),
    name = this.displayName(),
  )
}
