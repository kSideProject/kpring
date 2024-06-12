package kpring.core.server.dto.request

data class CreateServerRequest(
  val serverName: String,
  val userId: String,
  val theme: String? = null,
  val categories: List<String> = listOf(),
)
