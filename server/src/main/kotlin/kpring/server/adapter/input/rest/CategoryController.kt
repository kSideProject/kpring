package kpring.server.adapter.input.rest

import kpring.core.global.dto.response.ApiResponse
import kpring.server.application.port.input.GetCategoryUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/category")
class CategoryController(
  val getCategoryUseCase: GetCategoryUseCase,
) {
  @GetMapping("")
  fun getCategories(): ResponseEntity<ApiResponse<*>> {
    val response = getCategoryUseCase.getCategories()
    return ResponseEntity.ok()
      .body(ApiResponse(data = response))
  }
}
