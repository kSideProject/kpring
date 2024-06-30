package kpring.core.server.dto.request

data class CreateServerRequest(
  val serverName: String,
  val userId: String,
  val hostName: String,
  val theme: String? = null,
  val categories: List<String>? = null,
)
