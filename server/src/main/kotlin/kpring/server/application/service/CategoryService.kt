package kpring.server.application.service

import kpring.core.server.dto.CategoryInfo
import kpring.server.application.port.input.GetCategoryUseCase
import kpring.server.domain.Category
import org.springframework.stereotype.Service

@Service
class CategoryService : GetCategoryUseCase {
  override fun getCategories(): List<CategoryInfo> {
    return Category.entries.map { category ->
      CategoryInfo(
        id = category.name,
        name = category.toString(),
      )
    }
  }
}
