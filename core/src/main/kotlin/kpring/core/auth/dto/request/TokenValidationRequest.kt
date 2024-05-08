package kpring.core.auth.dto.request

import jakarta.validation.constraints.NotBlank

@Deprecated("삭제 예정입니다.")
data class TokenValidationRequest(
  @NotBlank
  val userId: String,
)
