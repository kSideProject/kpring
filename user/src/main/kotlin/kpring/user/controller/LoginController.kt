package kpring.user.controller

import kpring.core.global.dto.response.ApiResponse
import kpring.user.dto.request.LoginRequest
import kpring.user.dto.request.LogoutRequest
import kpring.user.service.LoginService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class LoginController(val loginService: LoginService) {
  @PostMapping("/login")
  fun login(
    @RequestBody request: LoginRequest,
  ): ResponseEntity<ApiResponse<*>> {
    val response = loginService.login(request)
    return ResponseEntity
      .ok()
      .header("Authorization", response.accessToken)
      .body(ApiResponse(data = response))
  }

  @PostMapping("/logout")
  fun logout(
    @RequestBody request: LogoutRequest,
  ): ResponseEntity<ApiResponse<Any>> {
    loginService.logout(request)
    return ResponseEntity.ok().build()
  }
}
