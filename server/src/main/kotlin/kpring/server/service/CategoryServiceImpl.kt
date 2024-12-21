package kpring.server.service

import kpring.core.server.dto.CategoryInfo
import kpring.server.domain.Category
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl :
  CategoryService {
  override fun getCategories(): List<CategoryInfo> {
    return Category.entries.map { category ->
      CategoryInfo(
        id = category.name,
        name = category.toString(),
      )
    }
  }
}
