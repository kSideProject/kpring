package kpring.core.auth.dto.response

import kpring.core.auth.enums.TokenType

data class TokenValidationResponse(
    val isValid: Boolean,
    val type: TokenType?,
    val userId: String?
)