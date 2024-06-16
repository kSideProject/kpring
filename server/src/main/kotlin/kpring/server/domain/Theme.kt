package kpring.server.domain

enum class Theme(
  val title: String,
) {
  SERVER_THEME_001("숲"),
  SERVER_THEME_002("오피스"),
  ;

  companion object {
    fun default() = SERVER_THEME_001
  }

  fun id(): String = this.name

  fun displayName(): String = this.title
}
