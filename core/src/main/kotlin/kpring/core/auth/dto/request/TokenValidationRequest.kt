package kpring.core.auth.dto.request

import jakarta.validation.constraints.NotBlank

data class TokenValidationRequest(
    @NotBlank
    val userId: String
)
