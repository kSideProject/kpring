package kpring.auth.api.v2

import kpring.auth.service.TokenService
import kpring.core.auth.dto.response.TokenInfo
import kpring.core.global.dto.response.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController("AuthControllerV2")
@RequestMapping("/api/v2")
class AuthController(
  private val tokenService: TokenService,
) {
  @PostMapping("/validation")
  suspend fun validateToken(
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<ApiResponse<TokenInfo>> {
    val response = tokenService.checkToken(token.removePrefix("Bearer "))
    return ResponseEntity.ok()
      .body(ApiResponse(data = response))
  }
}
