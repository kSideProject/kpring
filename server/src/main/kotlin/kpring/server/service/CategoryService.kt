package kpring.server.service

import kpring.core.server.dto.CategoryInfo

interface CategoryService {
  fun getCategories(): List<CategoryInfo>
}
