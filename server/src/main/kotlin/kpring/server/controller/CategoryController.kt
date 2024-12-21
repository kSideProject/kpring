package kpring.server.controller

import kpring.core.global.dto.response.ApiResponse
import kpring.server.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/category")
class CategoryController(
  val categoryService: CategoryService,
) {
  @GetMapping("")
  fun getCategories(): ResponseEntity<ApiResponse<*>> {
    val response = categoryService.getCategories()
    return ResponseEntity.ok()
      .body(ApiResponse(data = response))
  }
}
