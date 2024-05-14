package kpring.core.server.dto

/**
 * @property id user 식별자
 * @property name 서버에서 사용중인 유저의 이름으로 실제 유저의 이름과 다를 수 있다.
 * @property profileImage 유저 프로필 이미지 url
 */
data class ServerUserInfo(
  val id: String,
  val name: String,
  val profileImage: String,
)
