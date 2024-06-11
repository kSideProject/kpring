package kpring.server.domain

/**
 * 2024-06-11 기준으로 카테고리와 관련된 기능이 없기 때문에 하드 코딩된 제한적인 카테고리를 사용합니다.
 * 카테고리의 기능이 확장된다면 해당 클래스는 deprecated 처리되며 해당 정보는 DB에서 관리될 예정입니다.
 */
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
