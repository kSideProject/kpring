package kpring.core.global.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse<T>(
  val status: Int? = 0,
  val message: String = "",
  val data: T? = null,
)
