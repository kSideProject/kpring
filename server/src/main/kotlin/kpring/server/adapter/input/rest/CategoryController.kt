package kpring.server.adapter.input.rest

import kpring.core.global.dto.response.ApiResponse
import kpring.core.server.dto.CategoryInfo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/category")
class CategoryController() {
  @GetMapping("")
  fun getCategories(): ResponseEntity<ApiResponse<*>> {
    val response =
      listOf(
        CategoryInfo("카테고리1"),
        CategoryInfo("카테고리2"),
      )
    return ResponseEntity.ok()
      .body(ApiResponse(data = response))
  }
}
