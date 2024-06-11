package kpring.server.domain

enum class Category(
  private val toString: String,
) {
  SERVER_CATEGORY1("학습"),
  SERVER_CATEGORY2("운동"),
  ;

  override fun toString(): String {
    return toString
  }
}
