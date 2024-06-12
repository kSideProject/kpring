package kpring.server.application.port.input

import kpring.core.server.dto.CategoryInfo

interface GetCategoryUseCase {
  fun getCategories(): List<CategoryInfo>
}
