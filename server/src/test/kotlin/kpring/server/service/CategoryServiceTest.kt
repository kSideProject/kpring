package kpring.server.service

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kpring.core.server.dto.CategoryInfo
import kpring.server.domain.Category

class CategoryServiceTest : FunSpec({

  test("카테고리 목록 조회시 하드 코딩된 카테고리 정보를 조회한다.") {
    // given
    val categoryService = CategoryServiceImpl()

    // when
    val categories = categoryService.getCategories()

    // then
    categories shouldBe
      listOf(
        CategoryInfo(
          id = Category.SERVER_CATEGORY1.name,
          name = Category.SERVER_CATEGORY1.toString(),
        ),
        CategoryInfo(
          id = Category.SERVER_CATEGORY2.name,
          name = Category.SERVER_CATEGORY2.toString(),
        ),
      )
    println(categories)
  }
})
