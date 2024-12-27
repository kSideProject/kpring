package kpring.core.user.dto.response

data class FailMessageResponse(
  val message: String,
) {
  companion object {
    val serverError = FailMessageResponse(message = "서버 오류")
  }
}
