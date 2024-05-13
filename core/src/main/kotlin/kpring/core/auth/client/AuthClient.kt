package kpring.core.auth.client

import kpring.core.auth.dto.request.CreateTokenRequest
import kpring.core.auth.dto.response.CreateTokenResponse
import kpring.core.auth.dto.response.ReCreateAccessTokenResponse
import kpring.core.auth.dto.response.TokenInfo
import kpring.core.auth.dto.response.TokenValidationResponse
import kpring.core.global.dto.response.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.service.annotation.DeleteExchange
import org.springframework.web.service.annotation.PostExchange

interface AuthClient {
  @PostExchange("/api/v1/token")
  fun createToken(
    @RequestBody request: CreateTokenRequest,
  ): ResponseEntity<CreateTokenResponse>

  @DeleteExchange("/api/v1/token/{tokenData}")
  fun deleteToken(
    @PathVariable("tokenData") tokenData: String,
  ): ResponseEntity<Void>

  @PostExchange("/api/v1/token/access_token")
  fun recreateAccessToken(
    @RequestHeader request: CreateTokenRequest,
  ): ResponseEntity<ReCreateAccessTokenResponse>

  @Deprecated("api 스펙 변경으로 삭제예정입니다.")
  @PostExchange("/api/v1/validation")
  fun validateToken(
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<TokenValidationResponse>

  @PostExchange("/api/v2/validation")
  fun getTokenInfo(
    @RequestHeader("Authorization") token: String,
  ): ApiResponse<TokenInfo>
}
