package kpring.core.auth.dto.request

import jakarta.validation.constraints.NotBlank

data class CreateTokenRequest(
  @field:NotBlank
  val id: String,
  @field:NotBlank
  val nickname: String,
)
